package com.foreflight.foreflighttest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportInfo {
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("name")
    private String name;
    @JsonProperty("faaCode")
    private String id;
    @JsonProperty("runways")
    private List<Runway> runways;
}
