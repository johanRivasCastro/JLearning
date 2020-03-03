package com.johanrivas.jlearning.Execptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UniqueConstraintViolationAdvice {

    @ResponseBody
    @ExceptionHandler(UniqueConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleUniqueConstraintViolation(UniqueConstraintViolationException ex) {
        return ex.getMessage();
    }
}
