package com.sedeeman.ca.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlightTest {

    private Flight flight;

    private Validator validator;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        flight.setFlightId(1L);
        flight.setFlightNumber("ABC123");
        flight.setOriginLocation("Toronto");
        flight.setDestinationLocation("Vancouver");
        flight.setFlightType(FlightType.INBOUND);
        flight.setTerminalGate("A12");
        flight.setArrivalTime(LocalDateTime.now());
        flight.setDepartureTime(LocalDateTime.now().plusHours(1));
        flight.setStatus(FlightStatus.DELAY);
        flight.setDelayed(true);

        assertEquals(1L, flight.getFlightId());
        assertEquals("ABC123", flight.getFlightNumber());
        assertEquals("Toronto", flight.getOriginLocation());
        assertEquals("Vancouver", flight.getDestinationLocation());
        assertEquals(FlightType.INBOUND, flight.getFlightType());
        assertEquals("A12", flight.getTerminalGate());
        assertNotNull(flight.getArrivalTime());
        assertNotNull(flight.getDepartureTime());
        assertEquals(FlightStatus.DELAY, flight.getStatus());
        assertTrue(flight.isDelayed());
    }

    @Test
    @DisplayName("Test equals and hashCode methods")
    void testEqualsAndHashCode() {
        Flight flight1 = new Flight();
        flight1.setFlightId(1L);
        flight1.setFlightNumber("Boeing737");

        Flight flight2 = new Flight();
        flight2.setFlightId(1L);
        flight2.setFlightNumber("Boeing737");

        assertEquals(flight1, flight2);
        assertEquals(flight1.hashCode(), flight2.hashCode());

        flight2.setFlightNumber("XYZ789");
        assertNotEquals(flight1, flight2);
        assertNotEquals(flight1.hashCode(), flight2.hashCode());
    }


    @Test
    @DisplayName("Test validation annotations")
    void testValidationAnnotations() {
        Flight invalidFlight = new Flight(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Set<ConstraintViolation<Flight>> violations = validator.validate(invalidFlight);
        assertEquals(11, violations.size());

        Flight validFlight = new Flight(
                1L,
                "ABC123",
                "Toronto",
                "Vancouver",
                FlightType.INBOUND,
                "A12",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                FlightStatus.DELAY,
                true
        );

        assertTrue(validator.validate(validFlight).isEmpty());
    }


}

