package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchCriteria {
    private String flightNumber;
    private String flightType;
    private String airportCode;
    private String airportName;
    private String location;
    private String status;
    private LocalDateTime scheduledTimeFrom;
    private LocalDateTime scheduledTimeTo;


    public FlightType getFlightTypeAsEnum() {
        if (flightType == null || flightType.isEmpty()) {
            return null;
        }

        try {
            return FlightType.valueOf(flightType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public FlightStatus getStatusAsEnum() {
        if (status == null || status.isEmpty()) {
            return null;
        }

        try {
            return FlightStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}