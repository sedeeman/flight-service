package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import com.sedeeman.ca.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        LocalDateTime currentSystemTime = LocalDateTime.now();

        return flightRepository.findAll()
                .stream()
                .map(flight -> {
                    flight.setDelayed(flight.getScheduledTime().isBefore(currentSystemTime));
                    return flight;
                })
                .toList();
    }

    public Flight addFlight(FlightAddRequest flightAddRequest) {
        try {
            Flight flight = new Flight();
            flight.setFlightNumber(flightAddRequest.getFlightNumber());
            flight.setScheduledTime(flightAddRequest.getScheduledTime());
            flight.setFlightType(flightAddRequest.getFlightType());
            flight.setAirportCode(flightAddRequest.getAirportCode());
            flight.setAirportName(flightAddRequest.getAirportName());
            flight.setLocation(flightAddRequest.getLocation());
            if(flight.getFlightType() == FlightType.INBOUND)flight.setStatus(FlightStatus.ARRIVAL);
            if(flight.getFlightType() == FlightType.OUTBOUND)flight.setStatus(FlightStatus.DEPARTURE);

            logger.info("Successfully added a new flight!");

            return flightRepository.save(flight);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error occurred in adding flight due to duplicate entry for flight number");
            throw e;
        }
    }

}
