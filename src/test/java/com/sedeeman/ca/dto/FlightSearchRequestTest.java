package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FlightSearchRequest Tests")
class FlightSearchRequestTest {

    @Test
    @DisplayName("Test Conversion of FlightType String to Enum")
    void testGetFlightTypeAsEnum() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFlightType("outbound");

        FlightType expected = FlightType.OUTBOUND;
        FlightType actual = request.getFlightTypeAsEnum();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test Conversion of FlightStatus String to Enum")
    void testGetStatusAsEnum() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setStatus("delay");

        FlightStatus expected = FlightStatus.DELAY;
        FlightStatus actual = request.getStatusAsEnum();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test Conversion with Empty FlightType String")
    void testGetFlightTypeAsEnumWithEmptyString() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFlightType("");

        FlightType actual = request.getFlightTypeAsEnum();

        assertNull(actual);
    }

    @Test
    @DisplayName("Test Conversion with Invalid FlightType String")
    void testGetFlightTypeAsEnumWithInvalidString() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFlightType("invalid");

        FlightType actual = request.getFlightTypeAsEnum();

        assertNull(actual);
    }

    @Test
    @DisplayName("Test Conversion with Empty FlightStatus String")
    void testGetStatusAsEnumWithEmptyString() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setStatus("");

        FlightStatus actual = request.getStatusAsEnum();

        assertNull(actual);
    }

    @Test
    @DisplayName("Test Conversion with Invalid FlightStatus String")
    void testGetStatusAsEnumWithInvalidString() {
        FlightSearchRequest request = new FlightSearchRequest();
        request.setStatus("invalid");

        FlightStatus actual = request.getStatusAsEnum();

        assertNull(actual);
    }
}
