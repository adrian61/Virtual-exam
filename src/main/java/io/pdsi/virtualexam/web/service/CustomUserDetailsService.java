package io.pdsi.virtualexam.web.service;


import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.core.jpa.repository.ExaminerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final ExaminerRepository examinerRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Examiner examiner = examinerRepository.findByLogin(login);
		if (examiner == null) {
			throw new UsernameNotFoundException("User " + login + "not found.");
		} else {
			return examiner;
		}
	}

	@Transactional
	public Examiner loadUserById(Integer id) {
		Examiner examiner = examinerRepository.getById(id);
		if (examiner == null) {
			throw new UsernameNotFoundException("User with id = " + id + "not found.");
		} else {
			return examiner;
		}
	}
}