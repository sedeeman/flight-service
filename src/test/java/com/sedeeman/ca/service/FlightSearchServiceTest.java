package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FlightSearchServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightSearchService flightSearchService;

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

        FlightSearchCriteria criteria = new FlightSearchCriteria();
        criteria.setFlightNumber(flightNumber);
        criteria.setAirportCode(airportCode);
        criteria.setAirportName(airportName);
        criteria.setStatus(status);
        criteria.setScheduledTimeFrom(scheduledTimeFrom);
        criteria.setScheduledTimeTo(scheduledTimeTo);

        List<Flight> expectedFlights = new ArrayList<>();

        when(flightRepository.searchFlights(flightNumber, airportCode, airportName, String.valueOf(status), scheduledTimeFrom, scheduledTimeTo))
                .thenReturn(expectedFlights);

        List<Flight> actualFlights = flightSearchService.searchFlights(criteria);

        assertEquals(expectedFlights, actualFlights);
    }
}
