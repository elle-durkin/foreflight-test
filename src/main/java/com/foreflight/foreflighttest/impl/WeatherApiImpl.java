package com.foreflight.foreflighttest.impl;

import com.foreflight.foreflighttest.config.ConfigProperties;
import com.foreflight.foreflighttest.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
//        String url = String.format("https://qa.foreflight.com/weather/report/%s",airportCode);
        String url = String.format("https://qa.foreflight.com/weather/report/kaus");
        HttpHeaders headers = new HttpHeaders();
        headers.add("ff-coding-exercise", String.valueOf(HEADER_VALUE));
        HttpEntity<HttpHeaders> headersEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam("ff-coding-exercise",HEADER_VALUE);

//        ResponseEntity<Weather> response = restTemplate.getForEntity(url, Weather.class, headersEntity);
        ResponseEntity<Weather> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, headersEntity,Weather.class);
        System.out.println(response.getBody());
        this.weather = response.getBody();
        return weather;
    }
}