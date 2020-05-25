package io.pdsi.virtualexam.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ApplicationController {

	@GetMapping(value = "/index")
	public String showIndex() {
		return "index";
	}

//	@GetMapping(value = "/login")
//	public String loginPage(Model theModel) {
//		return "login";
//	}

	@GetMapping(value = "/logout")
	public String logoutPage(Model theModel) {
		return "redirect:index";
	}

	@GetMapping("/access_denied")
	public String showAccessDenied() {
		return "accessDenied";
	}

}
