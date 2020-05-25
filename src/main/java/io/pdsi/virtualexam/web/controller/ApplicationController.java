package io.pdsi.virtualexam.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/")
public class ApplicationController {

	@GetMapping(value = "/")
	public String showHomePage(Model model) {
		model.addAttribute("standardDate", new Date());
		model.addAttribute("localDateTime", LocalDateTime.now());
		model.addAttribute("localDate", LocalDate.now());
		model.addAttribute("timestamp", Instant.now());
		return "index";
	}

	//TODO Added temporarily
	@GetMapping(value = "/examCreatorModal")
	public String showExamCreatorModal() {
		return "examCreatorModal";
	}

	//TODO Added temporarily
	@GetMapping(value = "/studentExamView")
	public String showStudentExamView() {
		return "studentExamView";
	}

	//TODO Added temporarily
	@GetMapping(value = "/teacherExamListPanel")
	public String showTeacherExamListPanel() {
		return "teacherExamListPanel";
	}

	//TODO Added temporarily
	@GetMapping(value = "/teacherPanel")
	public String showTeacherPanel() {
		return "teacherPanel";
	}

//	@GetMapping(value = "/login")
//	public String loginPage(Model theModel) {
//		return "login";
//	}

	@GetMapping(value = "/logout")
	public String logoutPage() {
		return "redirect:index";
	}

	@GetMapping("/access_denied")
	public String showAccessDenied() {
		return "accessDenied";
	}

}
