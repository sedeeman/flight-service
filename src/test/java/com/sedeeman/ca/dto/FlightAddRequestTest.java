package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FlightAddRequest Tests")
class FlightAddRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Test Constructor with All Valid Arguments")
    void testConstructorWithAllValidArguments() {
        String flightNumber = "ABC123";
        String originLocation = "Toronto";
        String destinationLocation = "Vancouver";
        FlightType flightType = FlightType.INBOUND;
        String terminalGate = "A12";
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now();
        FlightStatus status = FlightStatus.SCHEDULED;

        FlightAddRequest request = new FlightAddRequest(flightNumber, originLocation, destinationLocation, flightType,
                terminalGate, departureTime, arrivalTime, status);

        Set<ConstraintViolation<FlightAddRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());

        assertEquals(flightNumber, request.getFlightNumber());
        assertEquals(originLocation, request.getOriginLocation());
        assertEquals(destinationLocation, request.getDestinationLocation());
        assertEquals(flightType, request.getFlightType());
        assertEquals(terminalGate, request.getTerminalGate());
        assertEquals(departureTime, request.getDepartureTime());
        assertEquals(arrivalTime, request.getArrivalTime());
        assertEquals(status, request.getStatus());
    }

    @Test
    @DisplayName("Test Constructor with Invalid Arguments")
    void testConstructorWithInvalidArguments() {
        FlightAddRequest request = new FlightAddRequest();

        Set<ConstraintViolation<FlightAddRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}
