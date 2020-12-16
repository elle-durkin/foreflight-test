package com.foreflight.foreflighttest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Forecast {
    private String time_offset;
    private String temp;
    private String wind_speed;
    private String wind_dir;
}
