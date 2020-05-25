package io.pdsi.virtualexam.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
	private final String code;
	private final String message;
}
