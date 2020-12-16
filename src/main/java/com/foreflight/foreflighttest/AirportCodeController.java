package com.foreflight.foreflighttest;

import com.foreflight.foreflighttest.impl.AirportInfoImpl;
import com.foreflight.foreflighttest.impl.WeatherApiImpl;
import com.foreflight.foreflighttest.model.AirportInfo;
import com.foreflight.foreflighttest.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AirportCodeController {

	@Autowired
	WeatherApiImpl weatherApi;

	@Autowired
	AirportInfoImpl airportInfoImpl;


	@GetMapping("/")
	public String airportCodeForm(Model model) {
		model.addAttribute("airportCode", new AirportCode());
		return "airportCode";
	}

	@PostMapping("/")
	public String airportCodeSubmit(@ModelAttribute AirportCode airportCode, Model model) {
		model.addAttribute("airportCode", airportCode);
////		Weather weather = weatherApi.getWeather(airportCode.getContent());
//		Weather weather = weatherApi.getWeather("kaus");
		AirportInfo airportInfo = airportInfoImpl.getAirportInfo("kaus");
		return "result";
	}

}
