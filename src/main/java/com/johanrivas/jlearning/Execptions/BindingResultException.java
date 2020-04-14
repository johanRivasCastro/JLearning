package com.johanrivas.jlearning.Execptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class BindingResultException extends RuntimeException {

	private Map<String, Object> response;

	public BindingResultException(BindingResult result) {
		setErrros(result);
		// String listString = String.join(", ", listErros);
		// super();
	}

	public void setErrros(BindingResult result) {
		response = new HashMap<>();
		List<String> errors = result.getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList());
		response.put("message", String.join(", ", errors));

	}

	public Map<String, Object> getListErrors() {

		return response;
	}
}
