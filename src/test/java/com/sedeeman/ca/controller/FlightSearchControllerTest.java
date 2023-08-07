package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlightSearchControllerTest {

    @Mock
    private FlightSearchService flightSearchService;

    @InjectMocks
    private FlightSearchController flightSearchController;

    @BeforeEach
    void setUp() {
        flightSearchService = mock(FlightSearchService.class);
        flightSearchController = new FlightSearchController(flightSearchService);
    }

    @Test
  void testSearchFlights() {
        String flightNumber = "ABC123";
        String airportCode = "JFK";
        String airportName = "John F. Kennedy International Airport";
        String status = "arrival";
        String flightType = "inbound";
        String location = "Vancouver";
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

        ResponseEntity<SuccessResponse<List<Flight>>> response = flightSearchController.searchFlights(
                flightNumber, flightType, airportCode, airportName, location, status, scheduledTimeFrom, scheduledTimeTo);

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
}
