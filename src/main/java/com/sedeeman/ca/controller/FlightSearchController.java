package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * Search flights
     *
     * @return List of flights
     */

    @Operation(description = "Search flights", responses = {
            @ApiResponse(
                    responseCode = "200",
                    ref = "getAllSuccessAPI"
            ),
            @ApiResponse(
                    responseCode = "204",
                    ref = "noContentAPI"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "internalServerErrorAPI"
            )

    })
    @GetMapping(value = "/flights/search")
    public ResponseEntity<SuccessResponse<List<Flight>>> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String flightType,
            @RequestParam(required = false) String airportCode,
            @RequestParam(required = false) String airportName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledTimeTo
    ) {
        FlightSearchCriteria criteria = new FlightSearchCriteria(flightNumber,flightType,airportCode,airportName, location,status,scheduledTimeFrom,scheduledTimeTo);
        List<Flight> searchedFlights = flightSearchService.searchFlights(criteria);

        if (searchedFlights.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Add a new Flight", searchedFlights), HttpStatus.OK);
        }
    }
}
