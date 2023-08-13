package com.sedeeman.ca.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Constant Test")
class ConstantTest {

    @Test
    @DisplayName("Test constant value")
    void testConstantValue() {
        assertEquals("default", Constant.DEFAULT);
    }

}

