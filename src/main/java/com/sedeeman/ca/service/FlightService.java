package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightAddRequest;
import com.sedeeman.ca.dto.FlightSearchRequest;
import com.sedeeman.ca.dto.FlightUpdateRequest;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightType;
import com.sedeeman.ca.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
    private final FlightRepository flightRepository;

    private final AmqpTemplate amqpTemplate;


    public List<Flight> getAllFlights() {
        return applyDelayLogic(flightRepository.findAll());
    }

    public Flight addFlight(FlightAddRequest flightAddRequest) {
        try {
            Flight flight = new Flight();
            flight.setFlightNumber(flightAddRequest.getFlightNumber());
            flight.setOriginLocation(flightAddRequest.getOriginLocation());
            flight.setDestinationLocation(flightAddRequest.getDestinationLocation());
            flight.setFlightType(flightAddRequest.getFlightType());
            flight.setTerminalGate(flightAddRequest.getTerminalGate());
            flight.setArrivalTime(flightAddRequest.getArrivalTime());
            flight.setDepartureTime(flightAddRequest.getDepartureTime());
            flight.setStatus(flightAddRequest.getStatus());

            logger.info("Successfully added a new flight!");

            return flightRepository.save(flight);
        } catch (DataIntegrityViolationException error) {
            logger.error("Error occurred in adding flight due to duplicate entry for flight number");
            throw error;
        }
    }

    public List<Flight> searchFlights(FlightSearchRequest searchRequest) {
        return applyDelayLogic(flightRepository.searchFlights(
                searchRequest.getFlightNumber(),
                searchRequest.getOriginLocation(),
                searchRequest.getDestinationLocation(),
                searchRequest.getTerminalGate(),
                searchRequest.getFlightTypeAsEnum(),
                searchRequest.getStatusAsEnum()));
    }

    @Transactional
    public void updateFlightStatus(Long flightId, FlightUpdateRequest flightUpdateRequest) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found with ID: " + flightId));

        flight.setStatus(flightUpdateRequest.getStatus());
        flightRepository.save(flight);

        // Producer send message to broker (RabbitMQ)
        amqpTemplate.convertAndSend("flight-status-exchange", "", flightUpdateRequest);
    }


    private List<Flight> applyDelayLogic(List<Flight> flights) {
        LocalDateTime currentSystemTime = LocalDateTime.now();
        return flights.stream()
                .map(flight -> {
                    if (FlightType.INBOUND.equals(flight.getFlightType()) && flight.getArrivalTime().isBefore(currentSystemTime)) {
                        flight.setDelayed(true);
                    }
                    if (FlightType.OUTBOUND.equals(flight.getFlightType()) && flight.getDepartureTime().isBefore(currentSystemTime)) {
                        flight.setDelayed(true);
                    }
                    return flight;
                })
                .toList();
    }

}
