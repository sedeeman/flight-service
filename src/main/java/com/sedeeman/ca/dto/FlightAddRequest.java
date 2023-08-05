package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.validation.constraints.Max;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightAddRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Max(value = 9999999999L)
    private Long flightId;

    @NonNull
    @NotBlank
    @Max(value = 9999999999L)
    private String flightNumber;

    @NonNull
    private LocalDateTime scheduledTime;

    @NonNull
    @Enumerated(EnumType.STRING)
    private FlightType flightType;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String airportCode;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String airportName;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String location;

}