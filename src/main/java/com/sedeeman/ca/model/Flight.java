package com.sedeeman.ca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.validation.constraints.Max;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Max(value = 9999999999L)
    @Column(name = "flight_id")
    private Long flightId;

    @NonNull
    @NotBlank
    @Max(value = 9999999999L)
    @Column(name = "flight_number", unique = true)
    private String flightNumber;

    @NonNull
    @Column(name = "schedule_time")
    private LocalDateTime scheduledTime;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flight_type")
    private FlightType flightType;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "airport_code")
    private String airportCode;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "airport_name")
    private String airportName;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String location;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus status;

    @NonNull
    @Column(name = "delayed")
    private boolean delayed;

}
