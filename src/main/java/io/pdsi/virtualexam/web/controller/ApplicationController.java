package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.web.service.ExamService;
import io.pdsi.virtualexam.web.service.ExaminerService;
import io.pdsi.virtualexam.web.service.FileStorageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ApplicationController {
	private final ExaminerService examinerService;
	private final ExamService examService;
	private final FileStorageService fileStorageService;


	@GetMapping(value = "/")
	public String showHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		model.addAttribute("standardDate", new Date());
		model.addAttribute("localDateTime", LocalDateTime.now());
		model.addAttribute("localDate", LocalDate.now());
		model.addAttribute("timestamp", Instant.now());
		model.addAttribute("exam", new ExamDto());
		if (userDetails != null) {
			Examiner examiner = Examiner.builder()
					.login(userDetails.getUsername())
					.password(userDetails.getPassword())
					.role(userDetails.getAuthorities().toString())
					.build();
			List<ExamDto> examDtoList = examService.getExamsByExaminer(examiner)
					.stream()
					.sorted(Comparator.comparing(ExamDto::getStartDate))
					.collect(Collectors.toList());
			int closestExamIndex = 0;
			for (int i = 0; i < examDtoList.size(); i++) {
				if (examDtoList.get(i).getStartDate().isAfter(ZonedDateTime.now())) {
					closestExamIndex = i;
					break;
				}
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
			model.addAttribute("closestExamTitle", examDtoList.get(closestExamIndex).getTitle());
			model.addAttribute("closestExamTime", examDtoList.get(closestExamIndex).getStartDate().format(formatter));
		}
		return "index";
	}


	@PostMapping(value = "/createExam")
	public String showExamCreatorModal(@AuthenticationPrincipal UserDetails userDetails,
	                                   @RequestParam("file") MultipartFile file,
	                                   @RequestParam("title") String title,
	                                   @RequestParam("password") String password,
	                                   @RequestParam("startDate") String startDate,
	                                   @RequestParam("endDate") String endDate,
	                                   HttpServletRequest request) {
		LocalDateTime localStartDate = LocalDateTime.parse(startDate);
		ZonedDateTime zonedStartDate = localStartDate.atZone(ZoneId.of("GMT+00:00"));
		LocalDateTime localEndDate = LocalDateTime.parse(endDate);
		ZonedDateTime zonedEndDate = localEndDate.atZone(ZoneId.of("GMT+00:00"));
		String fileName = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/upload/downloadFile/")
				.path(fileName)
				.toUriString();
		try {
			Examiner loggedUser = examinerService.findByLogin(userDetails.getUsername());
			ExamDto exam = ExamDto.builder()
					.title(title)
					.password(password)
					.startDate(zonedStartDate)
					.endDate(zonedEndDate)
					.build();
			examService.saveExamForExaminer(exam, loggedUser);
		} catch (NullPointerException e) {
			log.error("User not found");
			return "redirect:/";
		} catch (DataAccessException e) {
			log.error(e.getLocalizedMessage());
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
			Examiner examiner = Examiner.builder()
					.login(userDetails.getUsername())
					.password(userDetails.getPassword())
					.role(userDetails.getAuthorities().toString())
					.build();
			List<ExamDto> examDtoList = examService.getExamsByExaminer(examiner);
			model.addAttribute("examList", examDtoList);
			model.addAttribute("exam", new ExamDto());
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

	@GetMapping("/uploadForm")
	public String showUploadForm() {
		return "uploadform";
	}
}
