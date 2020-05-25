package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.core.jpa.repository.ExaminerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExaminerServiceImpl implements ExaminerService {
	private final ExaminerRepository examinerRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(Examiner examiner) {
		examiner.setPassword(bCryptPasswordEncoder.encode(examiner.getPassword()));
		examiner.setRole(examiner.getRole());
		examinerRepository.save(examiner);
	}

	@Override
	public Examiner findByLogin(String login) {
		return examinerRepository.findByLogin(login);
	}

	@Override
	public Examiner findByFirstNameAndLastName(String firstName, String lastName) {
		return examinerRepository.findByFirstNameAndLastName(firstName, lastName);
	}
}
