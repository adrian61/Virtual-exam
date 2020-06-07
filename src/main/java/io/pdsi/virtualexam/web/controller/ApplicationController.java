package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.api.dto.ExamPathDto;
import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import io.pdsi.virtualexam.web.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
	private final ExamPathService examPathService;
	private final StudentEntryService studentEntryService;


	@GetMapping(value = "/")
	public String showHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		model.addAttribute("standardDate", new Date());
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
	                                   @RequestParam("file") MultipartFile[] file,
	                                   @RequestParam("title") String title,
	                                   @RequestParam("password") String password,
	                                   @RequestParam("startDate") String startDate,
	                                   @RequestParam("endDate") String endDate,
	                                   HttpServletRequest request) {
		if (userDetails.getUsername() == null) return "redirect:/";
		LocalDateTime localStartDate = LocalDateTime.parse(startDate);
		ZonedDateTime zonedStartDate = localStartDate.atZone(ZoneId.of("GMT+00:00"));
		LocalDateTime localEndDate = LocalDateTime.parse(endDate);
		ZonedDateTime zonedEndDate = localEndDate.atZone(ZoneId.of("GMT+00:00"));
		List<String> paths = Arrays.stream(file)
				.map(this::uploadFile)
				.collect(Collectors.toList());
		try {
			Examiner loggedUser = examinerService.findByLogin(userDetails.getUsername());
			ExamDto exam = ExamDto.builder()
					.title(title)
					.password(password)
					.startDate(zonedStartDate)
					.endDate(zonedEndDate)
					.build();
			examService.saveExamForExaminer(exam, loggedUser);
			Exam examByExaminerAndTitle = examService.getExamByExaminerAndTitle(loggedUser, exam.getTitle());
			examPathService.saveGroups(examByExaminerAndTitle.getId(), paths);
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

	public String uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("upload/downloadFile/")
				.path(fileName)
				.toUriString();
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
			model.addAttribute("examToExamPanel", new ExamDto());
		} else {
			log.error("User not found");
		}
		return "examListPanel";
	}

	//TODO Added temporarily
	@PostMapping(value = "/examPanel")
	public String showTeacherPanel(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("exam") ExamDto exam, Model model) {
		if (exam == null || userDetails == null) return "redirect:/";
		ExamDto examDto = ExamDto.fromEntity(examService.getExam(exam.getId()));
		if (Duration.between(examDto.getEndDate(), ZonedDateTime.now()).isNegative()) return "redirect:/";
		List<ExamPathDto> pathListForExam = examPathService.getGroupsForExam(exam.getId());
		List<StudentEntry> participantList = studentEntryService.findByExamId(exam.toEntity());
		model.addAttribute("exam", examDto);
		model.addAttribute("standardDate", new Date());
		Duration duration = Duration.between(examDto.getEndDate(), ZonedDateTime.now());
		long seconds = Math.abs(duration.getSeconds());
		model.addAttribute("timeLeft", String.format(
				"%d:%02d:%02d",
				seconds / 3600,
				(seconds % 3600) / 60,
				seconds % 60));
		model.addAttribute("pathList", pathListForExam);
		model.addAttribute("participantList", participantList);
		return "examPanel";
	}

	@PostMapping(value = "/joinExam")
	public String joinExam(
	                        @RequestParam("title") String title,
	                        @RequestParam("password") String password,
	                        @RequestParam("index") Integer index,
	                        @RequestParam("firstName") String firstName,
	                        @RequestParam("lastName") String lastName
	){

		return "studentExamView";
	}


	@GetMapping(value = "/logout")
	public String logoutPage() {
		return "redirect:index";
	}

	@GetMapping("/access_denied")
	public String showAccessDenied() {
		return "accessDenied";
	}

}
