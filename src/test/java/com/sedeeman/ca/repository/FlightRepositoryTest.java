package com.sedeeman.ca.repository;

import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FlightRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testSearchFlights() {
        Flight flight = new Flight();
        flight.setFlightNumber("ABC123");
        flight.setFlightType(FlightType.INBOUND);
        flight.setAirportCode("JFK");
        flight.setAirportName("John F. Kennedy International Airport");
        flight.setLocation("Vancouver");
        flight.setStatus(FlightStatus.DEPARTURE);
        flight.setScheduledTime(LocalDateTime.of(2023, 8, 10, 12, 0));

        entityManager.persist(flight);

        List<Flight> result = flightRepository.searchFlights(
                flight.getFlightNumber(),
                flight.getFlightType(),
                flight.getAirportCode(),
                flight.getAirportName(),
                flight.getLocation(),
                flight.getStatus(),
                flight.getScheduledTime(),
                flight.getScheduledTime()
        );

        assertThat(result).containsExactly(flight);
    }

    @Test
    void testSearchFlights_NoResults() {

        List<Flight> result = flightRepository.searchFlights(
                "nonexistent-flight-number",
                FlightType.INBOUND,
                "nonexistent-airport-code",
                "nonexistent-airport-name",
                "nonexistent-location",
                FlightStatus.DEPARTURE,
                LocalDateTime.of(2023, 8, 10, 0, 0),
                LocalDateTime.of(2023, 8, 15, 23, 59, 59)
        );

        assertThat(result).isEmpty();
    }
}
