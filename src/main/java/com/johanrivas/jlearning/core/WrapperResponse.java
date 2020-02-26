package com.johanrivas.jlearning.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @Getter
// @Setter
// @ToString
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class WrapperResponse {
    private boolean successful;
    private Object data;
    private String errorMessage;

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean getSuccessful() {
        return successful;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}