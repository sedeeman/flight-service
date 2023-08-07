package com.sedeeman.ca.repository;

import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE " +
            "(:flightNumber is null or f.flightNumber = :flightNumber) " +
            "and (:flightType is null or f.flightType = :flightType) " +
            "and (:airportCode is null or f.airportCode = :airportCode) " +
            "and (:airportName is null or f.airportName = :airportName) " +
            "and (:location is null or f.location = :location) " +
            "and (:status is null or f.status = :status) " +
            "and (:scheduledTimeFrom is null or f.scheduledTime >= :scheduledTimeFrom) " +
            "and (:scheduledTimeTo is null or f.scheduledTime <= :scheduledTimeTo)")
    List<Flight> searchFlights(
            String flightNumber,
            FlightType flightType,
            String airportCode,
            String airportName,
            String location,
            FlightStatus status,
            LocalDateTime scheduledTimeFrom,
            LocalDateTime scheduledTimeTo
    );
}
