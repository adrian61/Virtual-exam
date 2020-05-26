package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.web.service.ExaminerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/")
public class ApplicationController {
	@Autowired
	ExaminerService examinerService;

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
	@GetMapping(value = "/examListPanel")
	public String showTeacherExamListPanel(Model model) {
		return "examListPanel";
	}

	//TODO Added temporarily
	@GetMapping(value = "/examPanel")
	public String showTeacherPanel() {
		return "examPanel";
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
