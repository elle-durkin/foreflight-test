package com.foreflight.foreflighttest.impl;

import com.foreflight.foreflighttest.Calculate;
import com.foreflight.foreflighttest.config.ConfigProperties;
import com.foreflight.foreflighttest.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherApiImpl {

    public static final int HEADER_VALUE = 1;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigProperties configProperties;

    private Weather weather;
    private String username = configProperties.getUsername();
    private String password = configProperties.getPassword();


    public Weather getWeather(String airportCode){
        String url = String.format("https://qa.foreflight.com/weather/report/%s",airportCode);
        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth("ff-interview","@-*KzU.*dtP9dkoE7PryL2ojY!uDV.6JJGC9");
        headers.add("ff-coding-exercise", String.valueOf(HEADER_VALUE));
        HttpEntity<HttpHeaders> headersEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ff-coding-exercise",HEADER_VALUE);

        ResponseEntity<Weather> response = restTemplate.exchange(url, HttpMethod.GET, headersEntity,Weather.class);
        System.out.println(response.getBody());
        this.weather = response.getBody();
        Calculate calculate = new Calculate();
        Double origTemp=weather.getReport().getConditions().getTemp();
        Double origWindSpeed=weather.getReport().getConditions().getWind_speed();
        weather.getReport().getConditions().setTemp(calculate.convertTemp(origTemp));
        weather.getReport().getConditions().setWind_speed(calculate.convertSpeed(origWindSpeed));
        return weather;
    }
}