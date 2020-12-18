package com.foreflight.foreflighttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForecastConditions {

    @JsonProperty("temp")
    private Double temp;
    private Double wind_speed;
    private Integer wind_dir;
    private String date;
    @JsonProperty("date_offset")
    private String date_offset;

    @JsonProperty("wind")
    private void windDeserializer(Map<String, Object> wind) {
        this.wind_speed = (Double) wind.get("speedKts");
        this.wind_dir = (Integer) wind.get("direction");
    }

    @JsonProperty("period")
    private void periodDeserializer(Map<String, Object> period) {
        this.date = (String) period.get("dateStart");
    }

    @JsonProperty("dateIssued")
    public void setDate(String date){
        this.date=date;
    }
    @JsonIgnore
    @JsonProperty("dateIssued")
    public String getDate(){
        return date;
    }

}