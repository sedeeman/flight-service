package com.sedeeman.ca.model;

import com.sedeeman.ca.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class FlightTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        flight.setFlightId(1L);
        flight.setFlightNumber("ABC123");
        flight.setScheduledTime(LocalDateTime.now());
        flight.setFlightType(FlightType.INBOUND);
        flight.setAirportCode("Code1");
        flight.setAirportName("Airport1");
        flight.setLocation("Location1");
        flight.setStatus(FlightStatus.ARRIVAL);
        flight.setDelayed(false);
    }

    @Test
    void testGetAllFlights() {
        Flight flight1 = new Flight();
        flight1.setFlightNumber("ABC123");
        flight1.setDelayed(true);

        Flight flight2 = new Flight();
        flight2.setFlightNumber("XYZ456");
        flight2.setDelayed(false);

        List<Flight> flights = Arrays.asList(flight1, flight2);

        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightRepository.findAll();

        assertEquals(2, result.size());
        assertTrue(result.get(0).isDelayed());
        assertFalse(result.get(1).isDelayed());
    }

    @Test
    void testAddFlight() {
        Flight newFlight = new Flight();
        newFlight.setFlightNumber("DEF789");
        newFlight.setScheduledTime(LocalDateTime.now());
        newFlight.setFlightType(FlightType.OUTBOUND);
        newFlight.setAirportCode("Code2");
        newFlight.setAirportName("Airport2");
        newFlight.setLocation("Location2");
        newFlight.setStatus(FlightStatus.ARRIVAL);
        newFlight.setDelayed(false);

        when(flightRepository.save(any(Flight.class))).thenReturn(newFlight);

        Flight result = flightRepository.save(newFlight);

        assertNotNull(result);
        assertEquals("DEF789", result.getFlightNumber());
    }

    @Test
    void testFindFlightById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Optional<Flight> result = flightRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("ABC123", result.get().getFlightNumber());
    }

    @Test
    void testFindFlightByNonExistentId() {
        when(flightRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Flight> result = flightRepository.findById(2L);

        assertFalse(result.isPresent());
    }
}
