package com.sedeeman.ca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Max(value = 9999999999L)
    @Column(name = "flight_id")
    private Long flightId;

    @NotNull
    @NotBlank
    @Column(name = "flight_number", unique = true)
    private String flightNumber;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "origin_location")
    private String originLocation;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "destination_location")
    private String destinationLocation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flight_type")
    private FlightType flightType;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "terminal_gate")
    private String terminalGate;

    @NotNull
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus status;

    @NotNull
    @Column(name = "is_delayed")
    private boolean isDelayed;

}
