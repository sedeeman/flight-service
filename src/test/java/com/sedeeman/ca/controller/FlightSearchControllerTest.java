package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.service.FlightSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class FlightSearchControllerTest {

    @Mock
    private FlightSearchService flightSearchService;

    @InjectMocks
    private FlightSearchController flightSearchController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchFlights() {
        String flightNumber = "ABC123";
        String airportCode = "JFK";
        String airportName = "John F. Kennedy International Airport";
        FlightStatus status = FlightStatus.ARRIVAL;
        LocalDateTime scheduledTimeFrom = LocalDateTime.of(2023, 8, 10, 0, 0);
        LocalDateTime scheduledTimeTo = LocalDateTime.of(2023, 8, 15, 23, 59, 59);

        List<Flight> expectedFlights = new ArrayList<>();

        FlightSearchCriteria criteria = new FlightSearchCriteria();
        criteria.setFlightNumber(flightNumber);
        criteria.setAirportCode(airportCode);
        criteria.setAirportName(airportName);
        criteria.setStatus(status);
        criteria.setScheduledTimeFrom(scheduledTimeFrom);
        criteria.setScheduledTimeTo(scheduledTimeTo);

        when(flightSearchService.searchFlights(any(FlightSearchCriteria.class))).thenReturn(expectedFlights);

        ResponseEntity<List<Flight>> response = flightSearchController.searchFlights(
                flightNumber, airportCode, airportName, status, scheduledTimeFrom, scheduledTimeTo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedFlights, response.getBody());
    }
}
