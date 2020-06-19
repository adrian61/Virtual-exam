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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import java.util.concurrent.ThreadLocalRandom;
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
	private static final String redirectToMainPage = "redirect:/";


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
		if (userDetails.getUsername() == null) return redirectToMainPage;
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
			return redirectToMainPage;
		} catch (DataAccessException e) {
			log.error(e.getLocalizedMessage());
			return redirectToMainPage;
		}

		return redirectToMainPage;
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
		if (exam == null || userDetails == null) return redirectToMainPage;
		ExamDto examDto = ExamDto.fromEntity(examService.getExam(exam.getId()));
		if (Duration.between(examDto.getEndDate(), ZonedDateTime.now()).isNegative()) return redirectToMainPage;
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
			@RequestParam("lastName") String lastName,
			RedirectAttributes redirectAttributes) {
		try {
			ExamDto exam = examService.getExamByTitle(title);
			if (exam.getPassword().equals(password)) {
				List<ExamPathDto> examPathDtoList = examPathService.getGroupsForExam(exam.getId());
				Integer groupNumber = ThreadLocalRandom.current().nextInt(1, examPathDtoList.size() + 1);
				StudentEntry newStudent = StudentEntry.builder()
						.exam(exam.toEntity())
						.done(false)
						.extraTime(0)
						.finishDate(exam.getEndDate())
						.firstName(firstName)
						.index(index)
						.lastName(lastName)
						.groupNumber(groupNumber)
						.build();
				studentEntryService.addNewStudent(newStudent);
				//TODO check if it exists (student)
				redirectAttributes.addFlashAttribute("student", newStudent);
				redirectAttributes.addFlashAttribute("exam", exam);
				return "redirect:/studentExamView";
			} else return redirectToMainPage;
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		return redirectToMainPage;
	}

	@GetMapping(value = "/studentExamView")
	public String showStudentExamView(@ModelAttribute("student") StudentEntry student, @ModelAttribute("exam") ExamDto exam, Model model) {
		model.addAttribute("student", student);
		model.addAttribute("exam", exam);
		List<ExamPathDto> examPath = examPathService.getGroupsForExam(exam.getId());
		System.out.println("group" + student.getGroupNumber());
		String exercisePath = examPath.get(student.getGroupNumber()).getPath();
		model.addAttribute("exercisePath", exercisePath);
		return "studentExamView";
	}

	@GetMapping(value = "/logout")
	public String logoutPage() {
		return redirectToMainPage;
	}

	@GetMapping("/access_denied")
	public String showAccessDenied() {
		return "accessDenied";
	}

	@GetMapping(value = "/time-left")
	public String getTimeLeft(@RequestParam("title") String title, ModelMap map) {
		ExamDto examDto = examService.getExamByTitle(title);
		Duration duration = Duration.between(examDto.getEndDate(), ZonedDateTime.now());
		long seconds = duration.getSeconds() < 0 ? Math.abs(duration.getSeconds()) : 0;	// No timer for finished exams
		map.addAttribute("timeLeft", String.format(
				"%d:%02d:%02d",
				seconds / 3600,
				(seconds % 3600) / 60,
				seconds % 60));
		return "examPanel :: #time-left";
	}

	//TODO temporarily
	@GetMapping(value = "/test")
	public String showTestWebSocket() {
		return "test";
	}
}
