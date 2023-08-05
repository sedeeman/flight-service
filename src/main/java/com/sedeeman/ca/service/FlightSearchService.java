package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightSearchCriteria;
import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightSearchService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightSearchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> searchFlights(FlightSearchCriteria criteria) {
        return flightRepository.searchFlights(criteria.getFlightNumber(), criteria.getAirportCode(),
                criteria.getAirportName(), String.valueOf(criteria.getStatus()), criteria.getScheduledTimeFrom(),
                criteria.getScheduledTimeTo());
    }
}
