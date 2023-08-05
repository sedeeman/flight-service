package com.sedeeman.ca.repository;

import com.sedeeman.ca.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE " +
            "(:flightNumber IS NULL OR f.flightNumber = :flightNumber) AND " +
            "(:airportCode IS NULL OR f.airportCode = :airportCode) AND " +
            "(:airportName IS NULL OR f.airportName = :airportName) AND " +
            "(:status IS NULL OR f.status = :status) AND " +
            "(:scheduledTimeFrom IS NULL OR f.scheduledTime >= :scheduledTimeFrom) AND " +
            "(:scheduledTimeTo IS NULL OR f.scheduledTime <= :scheduledTimeTo)")
    List<Flight> searchFlights(String flightNumber, String airportCode, String airportName,
                               String status, LocalDateTime scheduledTimeFrom, LocalDateTime scheduledTimeTo);

}
