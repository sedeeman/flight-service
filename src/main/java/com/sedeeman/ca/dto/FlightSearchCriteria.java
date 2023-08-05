package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
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
    private String airportCode;
    private String airportName;
    private FlightStatus status;
    private LocalDateTime scheduledTimeFrom;
    private LocalDateTime scheduledTimeTo;
}
