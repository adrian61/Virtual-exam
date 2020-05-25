package io.pdsi.virtualexam.web.service;


import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.core.jpa.repository.ExaminerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private ExaminerRepository examinerRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) {
		Examiner examiner = examinerRepository.findByLogin(login);
		if (examiner == null) throw new UsernameNotFoundException(login);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(examiner.getRole()));
		return new org.springframework.security.core.userdetails.User(examiner.getLogin(), examiner.getPassword(), grantedAuthorities);
	}
}