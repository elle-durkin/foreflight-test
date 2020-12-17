package com.foreflight.foreflighttest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Runway {
    @JsonProperty("name")
    private String name;
    @JsonProperty("approachSlopeIndicatorBaseAvailable")
    private Boolean available;
}
