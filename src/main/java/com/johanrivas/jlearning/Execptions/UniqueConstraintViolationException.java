package com.johanrivas.jlearning.Execptions;

public class UniqueConstraintViolationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UniqueConstraintViolationException(String message) {
        super(message);
    }

}
