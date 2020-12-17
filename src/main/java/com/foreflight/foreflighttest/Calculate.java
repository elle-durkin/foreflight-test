package com.foreflight.foreflighttest;

import com.foreflight.foreflighttest.model.Conditions;
import org.springframework.beans.factory.annotation.Autowired;

public class Calculate {

    @Autowired
    Conditions conditions;

    public Double convertTemp(Double celciusTemp){

        double fahrenheitTemp =((celciusTemp*9)/5)+32;
        return fahrenheitTemp;
    }

    public double convertSpeed(double ktsSpeed){
        double mphSpeed = ktsSpeed * 1.15;

        return mphSpeed;
    }
}
