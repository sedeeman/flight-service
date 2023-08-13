package com.sedeeman.ca.repository;

import com.sedeeman.ca.model.Flight;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE " +
            "(:flightNumber is null or f.flightNumber = :flightNumber) " +
            "and (:originLocation is null or f.originLocation = :originLocation) " +
            "and (:destinationLocation is null or f.destinationLocation = :destinationLocation) " +
            "and (:flightType is null or f.flightType = :flightType) " +
            "and (:terminalGate is null or f.terminalGate = :terminalGate) " +
            "and (:status is null or f.status = :status) ")
    List<Flight> searchFlights(
            @Param("flightNumber") String flightNumber,
            @Param("originLocation") String originLocation,
            @Param("destinationLocation") String destinationLocation,
            @Param("terminalGate") String terminalGate,
            @Param("flightType") FlightType flightType,
            @Param("status") FlightStatus status
    );
}
