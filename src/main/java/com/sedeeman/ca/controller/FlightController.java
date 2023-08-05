package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


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
            return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), "Products are not available", flights), HttpStatus.NO_CONTENT);
        }

        logger.info("Flights fetched successfully");
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully retrieved flights", flights), HttpStatus.OK);
    }


    /**
     * Add a flight.
     *
     * @param flightAddRequest
     * @return Product
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
    public ResponseEntity<SuccessResponse<Flight>> createProduct(@RequestBody @Valid FlightAddRequest flightAddRequest) {

        Flight savedFlight = flightService.addFlight(flightAddRequest);
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), "Add a new product", savedFlight), HttpStatus.CREATED);
    }


}
