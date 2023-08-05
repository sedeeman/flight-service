package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @Autowired
    public FlightSearchController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    @RequestMapping(value = "/flights/search", method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airportCode,
            @RequestParam(required = false) String airportName,
            @RequestParam(required = false) FlightStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledTimeTo
    ) {
        FlightSearchCriteria criteria = new FlightSearchCriteria(flightNumber, airportCode, airportName, status, scheduledTimeFrom, scheduledTimeTo);
        List<Flight> flights = flightSearchService.searchFlights(criteria);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
