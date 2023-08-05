package com.sedeeman.ca.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightStatusTest {

    @Test
    void testEnumValues() {

        assertEquals(3, FlightStatus.values().length);

        assertEquals(FlightStatus.ARRIVAL, FlightStatus.valueOf("ARRIVAL"));
        assertEquals(FlightStatus.DEPARTURE, FlightStatus.valueOf("DEPARTURE"));
        assertEquals(FlightStatus.DELAY, FlightStatus.valueOf("DELAY"));
    }
}
