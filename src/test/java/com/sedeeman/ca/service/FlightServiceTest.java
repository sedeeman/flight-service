package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.dto.FlightSearchRequest;
import com.sedeeman.ca.dto.FlightUpdateRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import com.sedeeman.ca.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Flight Service Tests")
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AmqpTemplate amqpTemplate;

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightService(flightRepository, amqpTemplate);
    }

    @Test
    @DisplayName("Should mark Inbound flights as delayed if arrival time is before the current time")
    void shouldMarkInboundFlightsDelayed() {
        LocalDateTime now = LocalDateTime.now();

        Flight flight1 = createTestFlight(now.minusHours(10), now.minusHours(1), FlightType.INBOUND);
        Flight flight2 = createTestFlight(now.minusHours(10), now.plusHours(1), FlightType.INBOUND);

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2));

        List<Flight> actualResult = flightService.getAllFlights();

        Assertions.assertThat(actualResult).hasSize(2);
        Assertions.assertThat(actualResult.get(0).isDelayed()).isTrue();
        Assertions.assertThat(actualResult.get(1).isDelayed()).isFalse();
    }

    @Test
    @DisplayName("Should mark Outbound flights as delayed if departure time is before the current time")
    void shouldMarkOutboundFlightsDelayed() {
        LocalDateTime now = LocalDateTime.now();

        Flight flight1 = createTestFlight(now.minusHours(1), now.plusHours(10), FlightType.OUTBOUND);
        Flight flight2 = createTestFlight(now.plusHours(1), now.plusHours(10), FlightType.OUTBOUND);

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2));

        List<Flight> actualResult = flightService.getAllFlights();

        Assertions.assertThat(actualResult).hasSize(2);
        Assertions.assertThat(actualResult.get(0).isDelayed()).isTrue();
        Assertions.assertThat(actualResult.get(1).isDelayed()).isFalse();
    }

    @Test
    @DisplayName("Should return added flight")
    void shouldReturnAddedFlight() {
        FlightAddRequest request = createTestAddRequest(FlightType.INBOUND, FlightStatus.SCHEDULED);

        Flight expectedResult = createTestFlight(request.getDepartureTime(), request.getArrivalTime(), request.getFlightType());

        when(flightRepository.save(any(Flight.class))).thenReturn(expectedResult);

        Flight actualResult = flightService.addFlight(request);

        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }


    @Test
    @DisplayName("Should throw exception when flight is not added successfully")
    void shouldThrowExceptionWhenFlightNotAdded() {
        FlightAddRequest request = createTestAddRequest(FlightType.INBOUND, FlightStatus.SCHEDULED);

        when(flightRepository.save(any(Flight.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> flightService.addFlight(request));
    }


    @Test
    @DisplayName("Should filter the results based on search criteria")
    void shouldFilterResultsForSearchCriteria() {
        FlightSearchRequest criteria = new FlightSearchRequest();
        criteria.setFlightNumber("Boeing737");
        criteria.setOriginLocation("Toronto");
        criteria.setTerminalGate("A7");
        criteria.setFlightType("OUTBOUND");
        criteria.setStatus("SCHEDULED");

        List<Flight> expectedFlights = Arrays.asList(new Flight(), new Flight());

        when(flightRepository.searchFlights(any(), any(), any(), any(), any(), any())).thenReturn(expectedFlights);

        List<Flight> actualResult = flightService.searchFlights(criteria);

        assertEquals(expectedFlights, actualResult);
        verify(flightRepository).searchFlights(eq(criteria.getFlightNumber()), eq(criteria.getOriginLocation()), isNull(), eq(criteria.getTerminalGate()), eq(FlightType.OUTBOUND), eq(FlightStatus.SCHEDULED));
    }


    @Test
    void testSearchFlights() {
        List<Flight> flights = createMockFlights();

        when(flightRepository.searchFlights(any(), any(), any(), any(), any(), any())).thenReturn(flights);

        FlightSearchRequest searchRequest = new FlightSearchRequest();

        List<Flight> result = flightService.searchFlights(searchRequest);

        assertEquals(flights.size(), result.size());
        for (int i = 0; i < flights.size(); i++) {
            Flight expected = flights.get(i);
            Flight actual = result.get(i);
            assertEquals(expected.getFlightType(), actual.getFlightType());
            if (FlightType.INBOUND.equals(expected.getFlightType()) && expected.getArrivalTime().isBefore(LocalDateTime.now())) {
                assertTrue(actual.isDelayed());
            } else if (FlightType.OUTBOUND.equals(expected.getFlightType()) && expected.getDepartureTime().isBefore(LocalDateTime.now())) {
                assertTrue(actual.isDelayed());
            } else {
                assertFalse(actual.isDelayed());
            }
        }
    }


    @Test
    @DisplayName("Should update flight status and send message")
    void shouldUpdateFlightStatusAndSendMessage() {

        Long flightId = 1L;
        FlightUpdateRequest updateRequest = new FlightUpdateRequest();
        updateRequest.setStatus(FlightStatus.DELAY);

        Flight existingFlight = new Flight();
        existingFlight.setFlightId(flightId);
        existingFlight.setStatus(FlightStatus.SCHEDULED);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));

        flightService.updateFlightStatus(flightId, updateRequest);

        verify(flightRepository).findById(flightId);
        verify(flightRepository).save(existingFlight);
        verify(amqpTemplate).convertAndSend("flight-status-exchange", "", updateRequest);
    }

    @Test
    @DisplayName("Should throw exception when flight not found")
    void shouldThrowExceptionWhenFlightNotFound() {

        Long flightId = 1L;
        FlightUpdateRequest updateRequest = new FlightUpdateRequest();
        updateRequest.setStatus(FlightStatus.DELAY);

        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> flightService.updateFlightStatus(flightId, updateRequest));

        verify(flightRepository).findById(flightId);
        verify(flightRepository, never()).save(any());
        verifyNoMoreInteractions(flightRepository);
    }


    private Flight createTestFlight(LocalDateTime departureTime, LocalDateTime arrivalTime, FlightType flightType) {
        Flight flight = new Flight();
        flight.setFlightNumber("Boeing737");
        flight.setOriginLocation("Toronto");
        flight.setDestinationLocation("Vancouver");
        flight.setFlightType(flightType);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        return flight;
    }

    private List<Flight> createMockFlights() {
        List<Flight> flights = new ArrayList<>();

        Flight flight1 = new Flight();
        flight1.setFlightType(FlightType.INBOUND);
        flight1.setArrivalTime(LocalDateTime.now().minusHours(1));
        flights.add(flight1);

        Flight flight2 = new Flight();
        flight2.setFlightType(FlightType.OUTBOUND);
        flight2.setDepartureTime(LocalDateTime.now().minusHours(2));
        flights.add(flight2);
        return flights;
    }

    private FlightAddRequest createTestAddRequest(FlightType flightType, FlightStatus status) {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("Boeing737");
        request.setOriginLocation("Toronto");
        request.setDestinationLocation("Vancouver");
        request.setTerminalGate("A10");
        request.setArrivalTime(LocalDateTime.now());
        request.setDepartureTime(LocalDateTime.now().minusHours(10));
        request.setFlightType(flightType);
        request.setStatus(status);
        return request;
    }
}
