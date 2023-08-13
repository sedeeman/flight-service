package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.FlightType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightAddRequest {

    @NotNull
    @NotBlank
    private String flightNumber;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String originLocation;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String destinationLocation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FlightType flightType;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String terminalGate;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

}