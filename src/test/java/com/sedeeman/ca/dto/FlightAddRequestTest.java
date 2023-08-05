package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightAddRequestTest {

    @Test
    void testGettersAndSetters() {
        LocalDateTime scheduledTime = LocalDateTime.now();
        FlightType flightType = FlightType.INBOUND;

        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("ABC123");
        request.setScheduledTime(scheduledTime);
        request.setFlightType(flightType);
        request.setAirportCode("Code1");
        request.setAirportName("Airport1");
        request.setLocation("Location1");

        assertEquals("ABC123", request.getFlightNumber());
        assertEquals(scheduledTime, request.getScheduledTime());
        assertEquals(flightType, request.getFlightType());
        assertEquals("Code1", request.getAirportCode());
        assertEquals("Airport1", request.getAirportName());
        assertEquals("Location1", request.getLocation());
    }

    @Test
    void testValidation_Success() {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("ABC123");
        request.setScheduledTime(LocalDateTime.now());
        request.setFlightType(FlightType.INBOUND);
        request.setAirportCode("Code1");
        request.setAirportName("Airport1");
        request.setLocation("Location1");

        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        Validator validator = config.getValidator();
        Set<ConstraintViolation<FlightAddRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void testValidation_Failure_InvalidFlightNumber() {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("");
        request.setScheduledTime(LocalDateTime.now());
        request.setFlightType(FlightType.INBOUND);
        request.setAirportCode("Code1");
        request.setAirportName("Airport1");
        request.setLocation("Location1");

        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        Validator validator = config.getValidator();
        Set<ConstraintViolation<FlightAddRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
    }

}
