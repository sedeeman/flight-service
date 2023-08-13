package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.dto.FlightSearchRequest;
import com.sedeeman.ca.dto.FlightUpdateRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/flight-service")
@RequiredArgsConstructor
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;


    /**
     * Get all flights
     *
     * @return List of flights
     */
    @GetMapping
    @Operation(description = "Get a list of all flights", responses = {
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
    public ResponseEntity<SuccessResponse<List<Flight>>> getAllFlights() {

        List<Flight> flights = flightService.getAllFlights();

        if (CollectionUtils.isEmpty(flights)) {
            logger.warn("Flights are not available");
            return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), "Flights are not available", flights), HttpStatus.NO_CONTENT);
        }

        logger.info("Flights fetched successfully");
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully retrieved flights", flights), HttpStatus.OK);
    }


    /**
     * Add a flight.
     *
     * @param flightAddRequest
     * @return Flight
     */
    @PostMapping
    @Operation(description = "Successfully Add a new flight", responses = {
            @ApiResponse(
                    responseCode = "201",
                    ref = "postSuccessAPI"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "badRequestAPI"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "internalServerErrorAPI"
            )

    })
    public ResponseEntity<SuccessResponse<Flight>> addFlight(@Valid @RequestBody FlightAddRequest flightAddRequest) {

        Flight savedFlight = flightService.addFlight(flightAddRequest);
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), "Successfully added a new Flight", savedFlight), HttpStatus.CREATED);
    }

    /**
     * Update flight status.
     *
     * @param flightUpdateRequest
     * @return Flight
     */
    @PatchMapping("/{flightId}")
    @Operation(description = "Successfully update flight status", responses = {
            @ApiResponse(
                    responseCode = "200",
                    ref = "getAllSuccessAPI"
            ),
            @ApiResponse(
                    responseCode = "204",
                    ref = "noContentAPI"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "badRequestAPI"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "internalServerErrorAPI"
            )

    })
    public ResponseEntity<SuccessResponse<String>> updateFlightStatus(@PathVariable Long flightId, @RequestBody @Valid FlightUpdateRequest flightUpdateRequest) {

        try {
            flightService.updateFlightStatus(flightId, flightUpdateRequest);
            return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully updated flight status", String.valueOf(flightUpdateRequest.getStatus())), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    @GetMapping(value = "/search")
    public ResponseEntity<SuccessResponse<List<Flight>>> searchFlights(
            @RequestParam(name = "flightNumber" ,required = false) String flightNumber,
            @RequestParam(name = "originLocation", required = false) String originLocation,
            @RequestParam(name = "destinationLocation", required = false) String destinationLocation,
            @RequestParam(name = "terminalGate", required = false) String terminalGate,
            @RequestParam(name = "flightType", required = false) String flightType,
            @RequestParam(name = "status", required = false) String status
    ) {
        FlightSearchRequest searchRequest = new FlightSearchRequest(flightNumber, originLocation, destinationLocation, terminalGate, flightType, status);
        List<Flight> searchedFlights = flightService.searchFlights(searchRequest);

        if (searchedFlights.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully searched flights", searchedFlights), HttpStatus.OK);
        }
    }


}
