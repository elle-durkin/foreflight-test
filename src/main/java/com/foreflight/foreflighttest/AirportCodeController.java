package com.foreflight.foreflighttest;

import com.foreflight.foreflighttest.config.ConfigProperties;
import com.foreflight.foreflighttest.impl.AirportInfoImpl;
import com.foreflight.foreflighttest.impl.WeatherApiImpl;
import com.foreflight.foreflighttest.model.AirportInfo;
import com.foreflight.foreflighttest.model.ForecastConditions;
import com.foreflight.foreflighttest.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class AirportCodeController {

	@Autowired
	WeatherApiImpl weatherApi;

	@Autowired
	AirportInfoImpl airportInfoImpl;
	public static final int HEADER_VALUE = 1;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigProperties configProperties;

	private Weather weather;


	private AirportInfo airportInfo;

	@GetMapping("/airportInfo")
	public AirportInfo getAirportInfo(String airportCode){
		String url = String.format("https://qa.foreflight.com/airports/%s",airportCode);
//        String url = String.format("https://qa.foreflight.com/airports/kaus");
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("ff-interview","@-*KzU.*dtP9dkoE7PryL2ojY!uDV.6JJGC9");
		headers.add("ff-coding-exercise", String.valueOf(HEADER_VALUE));
		HttpEntity<HttpHeaders> headersEntity = new HttpEntity<>(headers);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam("ff-coding-exercise",HEADER_VALUE);

		ResponseEntity<AirportInfo> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, headersEntity,AirportInfo.class);
		System.out.println(response.getBody());
		this.airportInfo = response.getBody();
		return airportInfo;
	}

	@GetMapping("/weather")
	public Weather getWeather(String airportCode) throws ParseException {
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
		if (origTemp != null) {
			weather.getReport().getConditions().setTemp(calculate.convertTemp(origTemp));
		}
		if (origWindSpeed != null) {
			weather.getReport().getConditions().setWind_speed(calculate.convertSpeed(origWindSpeed));
		}

		List<ForecastConditions> forecastConditionsList=weather.getReport().getForecast().getConditionsList();
		for (int i=0; i<forecastConditionsList.size(); i++){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss+ssss");
			Date date1 = format.parse(forecastConditionsList.get(i).getDate());
			Date current_date = format.parse(weather.getReport().getConditions().getDate());
			DateTime dt1 = new DateTime(date1);
			DateTime dt2 = new DateTime(current_date);
			String hours=String.format("%02d", Hours.hoursBetween(dt2, dt1).getHours() % 24);
			String minutes=String.format("%02d", Minutes.minutesBetween(dt2, dt1).getMinutes() % 60);
			String diff=hours+":"+minutes;
			weather.getReport().getForecast().getConditionsList().get(i).setDate_offset(diff);


		}

		return weather;
	}

//	@GetMapping("/weather")
//	public String weatherForm(Model model) {
//		model.addAttribute("airportCode", new AirportCode());
//		return "airportCode";
//	}
//
//	@PostMapping("/weather")
//	public String weatherSubmit(@ModelAttribute AirportCode airportCode, Model model) {
//		model.addAttribute("airportCode", airportCode);
//		Weather weatherInfo = weatherApi.getWeather(airportCode.getContent());
//		return "result";
//	}


	//	@GetMapping("/")
//	public String airportCodeForm(Model model) {
//		model.addAttribute("airportCode", new AirportCode());
//		return "airportCode";
//	}
//
//	@PostMapping("/")
//	public String airportCodeSubmit(@ModelAttribute AirportCode airportCode, Model model) {
//		model.addAttribute("airportCode", airportCode);
//		AirportInfo airportInfo = airportInfoImpl.getAirportInfo(airportCode.getContent());
//		return "result";
//	}
//
//	@GetMapping("/")
//	public String weatherForm(Model model) {
//		model.addAttribute("airportCode", new AirportCode());
//		return "airportCode";
//	}
//
//	@PostMapping("/")
//	public String weatherSubmit(@ModelAttribute AirportCode airportCode, Model model) {
//		model.addAttribute("airportCode", airportCode);
//		Weather weatherInfo = weatherApi.getWeather(airportCode.getContent());
//		return "result";
//	}

}
