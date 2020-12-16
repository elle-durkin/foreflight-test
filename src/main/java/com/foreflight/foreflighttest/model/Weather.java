package com.foreflight.foreflighttest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
    private String temp;
    private String humidity;
    private String cloud_cover;
    private String visibility;
    private String wind_speed;
    private String wind_dir;


}
