package com.sedeeman.ca.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightTypeTest {

    @Test
    void testEnumValues() {

        assertEquals(2, FlightType.values().length);

        assertEquals(FlightType.INBOUND, FlightType.valueOf("INBOUND"));
        assertEquals(FlightType.OUTBOUND, FlightType.valueOf("OUTBOUND"));
    }
}