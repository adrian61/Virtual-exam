package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.web.service.ExamService;
import io.pdsi.virtualexam.web.service.ExaminerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ApplicationController {
	private final ExaminerService examinerService;
	private final ExamService examService;


	@GetMapping(value = "/")
	public String showHomePage(Model model) {
		model.addAttribute("standardDate", new Date());
		model.addAttribute("localDateTime", LocalDateTime.now());
		model.addAttribute("localDate", LocalDate.now());
		model.addAttribute("timestamp", Instant.now());
		return "index";
	}

	@PostMapping(value = "/createExam")
	public String showExamCreatorModal(@AuthenticationPrincipal UserDetails userDetails, ExamDto exam) {
		try {
			Examiner loggedUser = examinerService.findByLogin(userDetails.getUsername());
			//implemented for a moment cuz probably createColloquiumModal will be refactored
			exam.setStartDate(ZonedDateTime.now());
			exam.setEndDate(ZonedDateTime.now());
			examService.saveExamForExaminer(exam, loggedUser);
		} catch (NullPointerException e) {
			log.error("User not found");
			//TODO maybe in the future we can add warning alert with message
			return "redirect:/";
		} catch (DataAccessException e) {
			log.error(e.getLocalizedMessage());
			//TODO maybe in the future we can add warning alert with message
			return "redirect:/";
		}

		return "redirect:/";
	}

	//TODO Added temporarily
	@GetMapping(value = "/studentExamView")
	public String showStudentExamView() {
		return "studentExamView";
	}

	@GetMapping(value = "/examListPanel")
	public String showTeacherExamListPanel(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		if (userDetails != null) {
			//Here should works cast but idk why not working Examiner ex = (Examiner) UserDetails
			Examiner examiner = Examiner.builder()
					.login(userDetails.getUsername())
					.password(userDetails.getPassword())
					.role(userDetails.getAuthorities().toString())
					.build();
			List<ExamDto> examDtoList = examService.getExamsByExaminer(examiner);
			model.addAttribute("examList", examDtoList);
		} else {
			log.error("User not found");
		}
		return "examListPanel";
	}

	//TODO Added temporarily
	@GetMapping(value = "/examPanel")
	public String showTeacherPanel() {
		return "examPanel";
	}


	@GetMapping(value = "/logout")
	public String logoutPage() {
		return "redirect:index";
	}

	@GetMapping("/access_denied")
	public String showAccessDenied() {
		return "accessDenied";
	}
	//TODO Added temporarily
	@GetMapping("/404")
	public String showPageNotFound() {
		return "pageNotFound";
	}

}
