package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FlightSearchServiceTest {


    @InjectMocks
    private FlightSearchService flightSearchService;

    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository = mock(FlightRepository.class);
        flightSearchService = new FlightSearchService(flightRepository);
    }

    @Test
    void testSearchFlights() {
        FlightSearchCriteria criteria = new FlightSearchCriteria();

        List<Flight> expectedFlights = Arrays.asList(new Flight(), new Flight());

        when(flightRepository.searchFlights(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(expectedFlights);

        List<Flight> result = flightSearchService.searchFlights(criteria);

        assertEquals(expectedFlights, result);
        verify(flightRepository).searchFlights(
                criteria.getFlightNumber(),
                criteria.getFlightTypeAsEnum(),
                criteria.getAirportCode(),
                criteria.getAirportName(),
                criteria.getLocation(),
                criteria.getStatusAsEnum(),
                criteria.getScheduledTimeFrom(),
                criteria.getScheduledTimeTo()
        );
    }
}

