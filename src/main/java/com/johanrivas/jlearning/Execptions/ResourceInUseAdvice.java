package com.johanrivas.jlearning.Execptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceInUseAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleResourceInUseException(ResourceInUseException e) {
        return e.getMessage();
    }
}