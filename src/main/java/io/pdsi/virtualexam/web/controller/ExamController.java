package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.web.exception.ExamNotFoundException;
import io.pdsi.virtualexam.web.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {
	private final ExamService examService;

//	@GetMapping("/exam")
//	@CrossOrigin
//	public List<Exam> getExams() {
//		return examService.findAll();
//	}

	@PostMapping("/")
	@CrossOrigin
	public Exam addExam(@RequestBody Exam exam) {
		exam.setId(0);
		examService.saveExam(exam);
		return exam;
	}

	@PutMapping("/")
	@CrossOrigin
	public Exam updateExam(@RequestBody Exam exam) {
		examService.saveExam(exam);
		return exam;
	}

	@GetMapping("/exam")
	public List<ExamDto> handleGetExamByExaminerId(@AuthenticationPrincipal UserDetails userDetails) {
		System.out.println(userDetails.getUsername());
		Examiner user = (Examiner) userDetails;
		List<ExamDto> examDtoList = examService.getExamsByExaminer(user);

		return examDtoList;
	}

	@GetMapping("/exam/{id}")
	@CrossOrigin
	public Exam getExam(@PathVariable Integer id) {
		return examService.getExam(id);
	}

	@DeleteMapping("/exam/{id}")
	@CrossOrigin
	public String deleteExam(@PathVariable Integer id) throws ExamNotFoundException {
		Exam exam = examService.getExam(id);
		if (exam == null) {
			throw new ExamNotFoundException("Exam id not found - " + id);
		}
		examService.deleteExam(id);
		return "Deleted exam id" + id;
	}

}
