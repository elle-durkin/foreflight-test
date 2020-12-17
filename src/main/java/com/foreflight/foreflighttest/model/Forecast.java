package com.foreflight.foreflighttest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    private String time_offset;
    private String temp;
    private String wind_speed;
    private String wind_dir;
}
