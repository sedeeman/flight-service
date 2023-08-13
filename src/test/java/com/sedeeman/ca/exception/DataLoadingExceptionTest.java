package com.sedeeman.ca.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataLoadingExceptionTest {

    @Test
    @DisplayName("Test DataLoadingException")
    void testDataLoadingExceptionWithMessage() {
        String errorMessage = "Data loading failed";

        DataLoadingException exception = new DataLoadingException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }
}
