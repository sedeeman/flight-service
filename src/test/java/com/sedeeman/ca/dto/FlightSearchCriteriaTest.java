package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FlightSearchCriteriaTest {

    @Test
    public void testGettersAndSetters() {
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

        assertEquals(flightNumber, criteria.getFlightNumber());
        assertEquals(airportCode, criteria.getAirportCode());
        assertEquals(airportName, criteria.getAirportName());
        assertEquals(status, criteria.getStatus());
        assertEquals(scheduledTimeFrom, criteria.getScheduledTimeFrom());
        assertEquals(scheduledTimeTo, criteria.getScheduledTimeTo());
    }

    @Test
    public void testDefaultConstructor() {
        FlightSearchCriteria criteria = new FlightSearchCriteria();
        assertNull(criteria.getFlightNumber());
        assertNull(criteria.getAirportCode());
        assertNull(criteria.getAirportName());
        assertNull(criteria.getStatus());
        assertNull(criteria.getScheduledTimeFrom());
        assertNull(criteria.getScheduledTimeTo());
    }

    @Test
    public void testAllArgsConstructor() {
        String flightNumber = "ABC123";
        String airportCode = "JFK";
        String airportName = "John F. Kennedy International Airport";
        FlightStatus status = FlightStatus.ARRIVAL;
        LocalDateTime scheduledTimeFrom = LocalDateTime.of(2023, 8, 10, 0, 0);
        LocalDateTime scheduledTimeTo = LocalDateTime.of(2023, 8, 15, 23, 59, 59);

        FlightSearchCriteria criteria = new FlightSearchCriteria(flightNumber, airportCode, airportName,
                status, scheduledTimeFrom, scheduledTimeTo);

        assertEquals(flightNumber, criteria.getFlightNumber());
        assertEquals(airportCode, criteria.getAirportCode());
        assertEquals(airportName, criteria.getAirportName());
        assertEquals(status, criteria.getStatus());
        assertEquals(scheduledTimeFrom, criteria.getScheduledTimeFrom());
        assertEquals(scheduledTimeTo, criteria.getScheduledTimeTo());
    }
}
