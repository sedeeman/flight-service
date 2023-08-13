package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.dto.FlightSearchRequest;
import com.sedeeman.ca.dto.FlightUpdateRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Flight Controller Tests")
class FlightControllerTest {

    @Mock
    private FlightService flightService;

    private FlightController flightController;

    @BeforeEach
    void setUp() {
        flightController = new FlightController(flightService);
    }

    @Test
    @DisplayName("Should retrieve all flights successfully")
    void shouldRetrieveAllFlightsSuccessfully() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight());
        flights.add(new Flight());

        when(flightService.getAllFlights()).thenReturn(flights);

        ResponseEntity<SuccessResponse<List<Flight>>> responseEntity = flightController.getAllFlights();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SuccessResponse<List<Flight>> successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.OK.value(), successResponse.getCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Successfully retrieved flights", successResponse.getMessage());
        assertEquals(flights, successResponse.getData());
    }

    @Test
    @DisplayName("Should handle situations when no flights are available")
    void shouldHandleGetAllWithNoFlights() {
        when(flightService.getAllFlights()).thenReturn(new ArrayList<>());

        ResponseEntity<SuccessResponse<List<Flight>>> responseEntity = flightController.getAllFlights();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        SuccessResponse<List<Flight>> successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.NO_CONTENT.value(), successResponse.getCode());
        assertEquals(HttpStatus.NO_CONTENT.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Flights are not available", successResponse.getMessage());
        assertEquals(new ArrayList<>(), successResponse.getData());
    }

    @Test
    @DisplayName("Should add a new flight successfully")
    void shouldAddFlightSuccessfully() {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("Boeing737");

        Flight savedFlight = new Flight();
        savedFlight.setFlightId(1L);
        savedFlight.setFlightNumber("Boeing737");

        when(flightService.addFlight(any(FlightAddRequest.class))).thenReturn(savedFlight);

        ResponseEntity<SuccessResponse<Flight>> responseEntity = flightController.addFlight(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        SuccessResponse<Flight> successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.CREATED.value(), successResponse.getCode());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Successfully added a new Flight", successResponse.getMessage());

        Flight responseData = successResponse.getData();
        assertNotNull(responseData);
        assertEquals(savedFlight.getFlightId(), responseData.getFlightId());
        assertEquals(savedFlight.getFlightNumber(), responseData.getFlightNumber());
    }

    @Test
    @DisplayName("Should throw an exception when data integrity violation occurs")
    void shouldThrowExceptionWhenErrorOccurred() {
        FlightAddRequest request = new FlightAddRequest();

        when(flightService.addFlight(any(FlightAddRequest.class))).thenThrow(new DataIntegrityViolationException("Duplicate flight number"));

        assertThrows(DataIntegrityViolationException.class, () -> flightController.addFlight(request));
    }


    @Test
    @DisplayName("Should filter results based on search criteria")
    void shouldFilterSearchResults() {
        String flightNumber = "Boeing737";
        String originLocation = "Toronto";
        String destinationLocation = "Vancouver";
        String terminalGate = "A12";
        String flightType = "inbound";
        String status = "delay";

        List<Flight> expectedFlights = new ArrayList<>();

        FlightSearchRequest criteria = new FlightSearchRequest();
        criteria.setFlightNumber(flightNumber);
        criteria.setOriginLocation(originLocation);
        criteria.setDestinationLocation(destinationLocation);
        criteria.setTerminalGate(terminalGate);
        criteria.setFlightType(flightType);
        criteria.setStatus(status);

        when(flightService.searchFlights(any(FlightSearchRequest.class))).thenReturn(expectedFlights);

        ResponseEntity<SuccessResponse<List<Flight>>> response = flightController.searchFlights(
                flightNumber, originLocation, destinationLocation, terminalGate, flightType, status
        );

        if (expectedFlights.isEmpty()) {
            assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
            assertThat(response.getBody(), is(nullValue()));
        } else {
            assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
            assertThat(response.getBody(), is(notNullValue()));
            assertThat(response.getBody().getStatus(), equalTo(HttpStatus.OK.value()));
            assertThat(response.getBody().getMessage(), equalTo(HttpStatus.OK.getReasonPhrase()));
            assertThat(response.getBody().getData(), equalTo(expectedFlights));
        }
    }

    @Test
    @DisplayName("Should successfully update flight status")
    void shouldUpdateFlightStatusSuccessfully() {
        Long flightId = 1L;
        FlightUpdateRequest updateRequest = new FlightUpdateRequest();
        updateRequest.setStatus(FlightStatus.DELAY);

        doNothing().when(flightService).updateFlightStatus(flightId, updateRequest);

        ResponseEntity<SuccessResponse<String>> responseEntity = flightController.updateFlightStatus(flightId, updateRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SuccessResponse<String> successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.OK.value(), successResponse.getCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Successfully updated flight status", successResponse.getMessage());
        assertEquals(updateRequest.getStatus().toString(), successResponse.getData());
    }


    @Test
    @DisplayName("Should handle IllegalArgumentException and return BAD_REQUEST")
    void shouldHandleIllegalArgumentException() {
        Long flightId = 1L;
        FlightUpdateRequest updateRequest = new FlightUpdateRequest();
        updateRequest.setStatus(FlightStatus.DELAY);

        doThrow(new IllegalArgumentException("Invalid flight status"))
                .when(flightService).updateFlightStatus(flightId, updateRequest);

        ResponseEntity<SuccessResponse<String>> responseEntity = flightController.updateFlightStatus(flightId, updateRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }


}
