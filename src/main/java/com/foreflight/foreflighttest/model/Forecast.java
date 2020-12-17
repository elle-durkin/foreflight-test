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
public class Forecast {
//    private String time_offset;
//    private String temp;
//    private String wind_speed;
//    private String wind_dir;
    @JsonProperty("conditions")
    private List<ForecastConditions> conditionsList;

    public void setConditionsList(List<ForecastConditions> conditionsList){
        this.conditionsList = conditionsList.subList(1,3);
    }
}
