package com.foreflight.foreflighttest;

import com.foreflight.foreflighttest.model.Conditions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Calculates temperature and speed conversions
 */
public class Calculate {

    @Autowired
    Conditions conditions;

    /**
     * Converts temp from celcius to fahrenheit.
     *
     * @param celciusTemp the celcius temp
     * @return temp in fahrenheit
     */
    public Double convertTemp(Double celciusTemp){

        double fahrenheitTemp =((celciusTemp*9)/5)+32;
        return fahrenheitTemp;
    }

    /**
     * Convert speed from kts to mph.
     *
     * @param ktsSpeed the kts speed
     * @return the speed in mph
     */
    public double convertSpeed(double ktsSpeed){
        double mphSpeed = ktsSpeed * 1.15;

        return mphSpeed;
    }
}
