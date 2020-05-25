package io.pdsi.virtualexam.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
	private String code;
	private String message;
}
