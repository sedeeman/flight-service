package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        flightService = mock(FlightService.class);
        flightController = new FlightController(flightService);
    }

    @Test
    void testGetAllFlightsWithFlights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight());
        flights.add(new Flight());

        when(flightService.getAllFlights()).thenReturn(flights);

        ResponseEntity<SuccessResponse<List<Flight>>> responseEntity = flightController.getAllFlights();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SuccessResponse successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.OK.value(), successResponse.getCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Successfully retrieved flights", successResponse.getMessage());
        assertEquals(flights, successResponse.getData());
    }

    @Test
    void testGetAllFlightsWithNoFlights() {

        when(flightService.getAllFlights()).thenReturn(new ArrayList<>());

        ResponseEntity<SuccessResponse<List<Flight>>> responseEntity = flightController.getAllFlights();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        SuccessResponse successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.NO_CONTENT.value(), successResponse.getCode());
        assertEquals(HttpStatus.NO_CONTENT.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Products are not available", successResponse.getMessage());
        assertEquals(new ArrayList<>(),successResponse.getData());
    }


    @Test
    void testCreateProduct_Success() {
        FlightAddRequest request = new FlightAddRequest();
        request.setFlightNumber("ABC123");

        Flight savedFlight = new Flight();
        savedFlight.setFlightId(1L);
        savedFlight.setFlightNumber("ABC123");

        when(flightService.addFlight(any(FlightAddRequest.class))).thenReturn(savedFlight);

        ResponseEntity<SuccessResponse<Flight>> responseEntity = flightController.createProduct(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        SuccessResponse successResponse = responseEntity.getBody();
        assertNotNull(successResponse);
        assertEquals(HttpStatus.CREATED.value(), successResponse.getCode());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), successResponse.getStatus());
        assertEquals("Add a new product", successResponse.getMessage());

        Flight responseData = (Flight) successResponse.getData();
        assertNotNull(responseData);
        assertEquals(savedFlight.getFlightId(), responseData.getFlightId());
        assertEquals(savedFlight.getFlightNumber(), responseData.getFlightNumber());
    }

    @Test
    void testCreateProduct_Exception() {
        FlightAddRequest request = new FlightAddRequest();

        when(flightService.addFlight(any(FlightAddRequest.class))).thenThrow(new DataIntegrityViolationException("Duplicate flight number"));

        assertThrows(DataIntegrityViolationException.class, () -> flightController.createProduct(request));
    }

}
