package com.johanrivas.jlearning.Execptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BindingResultAdvice {

	@ResponseBody
	@ExceptionHandler(BindingResultException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	List<String> BadRequestAdvice(BindingResultException ex) {
		return ex.getListErrors();
	}
}
