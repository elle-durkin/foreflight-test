package com.foreflight.foreflighttest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AirportCodeController {

	@GetMapping("/")
	public String airportCodeForm(Model model) {
		model.addAttribute("airportCode", new AirportCode());
		return "airportCode";
	}

	@PostMapping("/")
	public String airportCodeSubmit(@ModelAttribute AirportCode airportCode, Model model) {
		model.addAttribute("airportCode", airportCode);
		return "result";
	}

}
