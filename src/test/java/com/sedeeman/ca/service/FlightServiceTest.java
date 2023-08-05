package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightType;
import com.sedeeman.ca.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllFlights() {
        Flight flight1 = new Flight();
        flight1.setScheduledTime(LocalDateTime.now().minusHours(1));

        Flight flight2 = new Flight();
        flight2.setScheduledTime(LocalDateTime.now().plusHours(1));

        List<Flight> flights = Arrays.asList(flight1, flight2);

        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightService.getAllFlights();

        assertEquals(2, result.size());
        assertTrue(result.get(0).isDelayed());
        assertFalse(result.get(1).isDelayed());
    }

    @Test
    void testAddFlight() {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("ABC123");
        request.setScheduledTime(LocalDateTime.now());
        request.setFlightType(FlightType.INBOUND);
        request.setAirportCode("Code1");
        request.setAirportName("Airport1");
        request.setLocation("Location1");

        Flight flight = new Flight();
        flight.setFlightNumber("ABC123");

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        Flight result = flightService.addFlight(request);

        assertNotNull(result);
        assertEquals("ABC123", result.getFlightNumber());
    }

    @Test
    void testAddFlight_DataIntegrityViolationException() {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("ABC123");
        request.setScheduledTime(LocalDateTime.now());
        request.setFlightType(FlightType.INBOUND);
        request.setAirportCode("Code1");
        request.setAirportName("Airport1");
        request.setLocation("Location1");

        when(flightRepository.save(any(Flight.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> flightService.addFlight(request));
    }
}
