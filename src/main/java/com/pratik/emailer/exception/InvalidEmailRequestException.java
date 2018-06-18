package com.pratik.emailer.exception;

import java.util.List;

public class InvalidEmailRequestException extends IllegalArgumentException {

    private List<String> errorMessages;

    public InvalidEmailRequestException(List<String> errorMessages) {
        super(String.join(",", errorMessages));
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
