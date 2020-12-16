package com.foreflight.foreflighttest;

public class Calculate {

    public int convertTemp(int celciusTemp){
        int fahrenheitTemp =((celciusTemp*9)/5)+32;

        return fahrenheitTemp;
    }

    public double convertSpeed(double ktsSpeed){
        double mphSpeed = ktsSpeed * 1.15;

        return mphSpeed;
    }
}
