package com.johanrivas.jlearning.Execptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class BindingResultException extends RuntimeException {

	private static List<String> listErros;

	public BindingResultException(BindingResult result) {
		setErrros(result);
	}

	public static void setErrros(BindingResult result) {
		List<String> errors = result.getFieldErrors().stream()
				.map(err -> err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
		listErros = errors;
	}

	public List<String> getListErrors() {
		return listErros;
	}
}
