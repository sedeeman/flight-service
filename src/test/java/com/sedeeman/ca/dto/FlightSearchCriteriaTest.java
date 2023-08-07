package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import com.sedeeman.ca.repository.FlightRepository;
import com.sedeeman.ca.service.FlightSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightSearchCriteriaTest {
    @InjectMocks
    private FlightSearchService flightSearchService;

    @Mock
    private FlightRepository flightRepository;

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

    @Test
    void testGetFlightTypeAsEnum() {
        FlightSearchCriteria criteria = new FlightSearchCriteria();
        criteria.setFlightType("inbound");

        FlightType expectedType = FlightType.INBOUND;
        FlightType actualType = criteria.getFlightTypeAsEnum();

        assertEquals(expectedType, actualType);
    }

    @Test
    void testGetStatusAsEnum() {
        FlightSearchCriteria criteria = new FlightSearchCriteria();
        criteria.setStatus("arrival");

        FlightStatus expectedStatus = FlightStatus.ARRIVAL;
        FlightStatus actualStatus = criteria.getStatusAsEnum();

        assertEquals(expectedStatus, actualStatus);
    }

}

