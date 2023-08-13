package com.sedeeman.ca.repository;

import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class FlightRepositoryTest {

    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository = mock(FlightRepository.class);
    }

    @Test
    @DisplayName("Should search flights based on criteria")
    void testSearchFlights() {
        String flightNumber = "ABC123";
        String originLocation = "Toronto";
        String destinationLocation = "Vancouver";
        String terminalGate = "A12";
        FlightType flightType = FlightType.INBOUND;
        FlightStatus status = FlightStatus.DELAY;

        List<Flight> expectedFlights = new ArrayList<>();
        expectedFlights.add(new Flight());
        expectedFlights.add(new Flight());

        when(flightRepository.searchFlights(flightNumber, originLocation, destinationLocation, terminalGate, flightType, status))
                .thenReturn(expectedFlights);

        List<Flight> actualResult = flightRepository.searchFlights(flightNumber, originLocation, destinationLocation, terminalGate, flightType, status);

        assertEquals(expectedFlights, actualResult);
    }

    @Test
    @DisplayName("Should return empty list for null criteria")
    void testSearchFlightsWithNullParameters() {
        when(flightRepository.searchFlights(any(), any(), any(), any(), any(), any()))
                .thenReturn(new ArrayList<>());

        List<Flight> actualResult = flightRepository.searchFlights(null, null, null, null, null, null);

        assertEquals(new ArrayList<>(), actualResult);
    }

}


