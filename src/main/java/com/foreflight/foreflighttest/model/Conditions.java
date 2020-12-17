package com.foreflight.foreflighttest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conditions {

    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("relativeHumidity")
    private Integer relativeHumidity;
    @JsonProperty("cloudLayers")
    private List cloud_cover;
    private Double visibilitySM;
    private Double wind_speed;
    private Integer wind_dir;
    @JsonProperty("dateIssued")
    private String date;

    @JsonProperty("temp")
    public Double getTemp(){
        return temp;
    }

    @JsonProperty("tempC")
    public void setTemp(Double temp){
        this.temp=temp;
    }

    @JsonProperty("visibility")
    private void visibilityDeserializer(Map<String, Double> visibility) {
        this.visibilitySM = (Double) visibility.get("distanceSm");
    }

    @JsonProperty("wind")
    private void windDeserializer(Map<String, Object> wind) {
        this.wind_speed = (Double) wind.get("speedKts");
        this.wind_dir = (Integer) wind.get("direction");
    }

}