package com.foreflight.foreflighttest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.foreflight.foreflighttest.config.ConfigProperties;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonRootName(value = "report")
public class Weather {

    @JsonProperty("conditions")
    private Conditions conditions;


}
