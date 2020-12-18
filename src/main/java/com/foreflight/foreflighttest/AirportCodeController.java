package com.foreflight.foreflighttest;

import com.foreflight.foreflighttest.config.ConfigProperties;
import com.foreflight.foreflighttest.model.AirportInfo;
import com.foreflight.foreflighttest.model.AllInformation;
import com.foreflight.foreflighttest.model.ForecastConditions;
import com.foreflight.foreflighttest.model.Weather;
import io.swagger.v3.oas.annotations.Parameter;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller for getting airport and weather information
 */
@RestController
public class AirportCodeController {

	public static final int HEADER_VALUE = 1;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private List<AllInformation> allInfo;

	@Autowired
	ConfigProperties config;

	private Weather weather;
	private AirportInfo airportInfo;

	/**
	 * Gets all info.
	 *
	 * @param airportCode - Airport code or comma separated list inputted from user
	 * @return the all info
	 * @throws ParseException the parse exception
	 */
	@GetMapping("/airport")
	public List<AllInformation> getAllInfo(@Parameter(description = "Airport code(s), example: aus,lax. To find identifiers: https://www.airnav.com/airports", name = "airportCode")String airportCode) throws ParseException {
		String codes[]=airportCode.split(",");
		for (int c=0; c <codes.length; c++){
			if (allInfo.size()<c+1){
				allInfo.add(new AllInformation());
			}

			allInfo.get(c).setAirportInfo(getAirportInfo(codes[c]));
			allInfo.get(c).setWeather(getWeather(codes[c]));
		}
		return allInfo;
	}


	/**
	 * Called by getAllInfo to return airport information
	 *
	 * @param airportCode the airport code
	 * @return the airport info
	 */
	public AirportInfo getAirportInfo(String airportCode){
		String url = String.format("https://qa.foreflight.com/airports/%s",airportCode);
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(config.getUsername(),config.getPassword());
		headers.add("ff-coding-exercise", String.valueOf(HEADER_VALUE));
		HttpEntity<HttpHeaders> headersEntity = new HttpEntity<>(headers);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

		ResponseEntity<AirportInfo> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, headersEntity,AirportInfo.class);
		this.airportInfo = response.getBody();
		return airportInfo;
	}


	/**
	 * Called by getAllInfo to return weather information
	 *
	 * @param airportCode the airport code
	 * @return the weather
	 * @throws ParseException the parse exception
	 */
	public Weather getWeather(String airportCode) throws ParseException {
		String url = String.format("https://qa.foreflight.com/weather/report/%s",airportCode);
		HttpHeaders headers = new HttpHeaders();

		headers.setBasicAuth(config.getUsername(),config.getPassword());
		headers.add("ff-coding-exercise", String.valueOf(HEADER_VALUE));
		HttpEntity<HttpHeaders> headersEntity = new HttpEntity<>(headers);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

		ResponseEntity<Weather> response = restTemplate.exchange(url, HttpMethod.GET, headersEntity,Weather.class);
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

}
