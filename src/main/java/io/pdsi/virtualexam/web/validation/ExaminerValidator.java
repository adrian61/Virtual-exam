package io.pdsi.virtualexam.web.validation;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.web.service.ExaminerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ExaminerValidator implements Validator {
	@Autowired
	private ExaminerService examinerService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Examiner.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Examiner examiner = (Examiner) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (examiner.getLogin().length() < 6 || examiner.getLogin().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		if (examinerService.findByLogin(examiner.getLogin()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (examiner.getPassword().length() < 8 || examiner.getPassword().length() > 32) {
			errors.rejectValue("password", "Length", "Password must be at least 8 characters and a maximum of 32 characters");
		}
		if (!examiner.getPasswordConfirm().equals(examiner.getPassword())) {
			errors.rejectValue("passwordConfirm", "Match", "Password must match.");
		}
	}
}
