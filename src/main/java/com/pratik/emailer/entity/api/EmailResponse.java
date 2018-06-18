package com.pratik.emailer.entity.api;

import java.util.ArrayList;
import java.util.List;

public class EmailResponse {

    private StatusCode status;
    private List<String> message;
    private String emailGateway;

    public EmailResponse(StatusCode status, String message) {
        this.status = status;
        this.message = new ArrayList<>();
        this.message.add(message);
    }

    public EmailResponse() {
        this.status = StatusCode.PENDING;
        this.message = new ArrayList<>();
    }

    public StatusCode getStatus() {
        return status;
    }

    public void setStatus(StatusCode status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getEmailGateway() {
        return emailGateway;
    }

    public void setEmailGateway(String emailGateway) {
        this.emailGateway = emailGateway;
    }

    public enum StatusCode {
        PENDING, OK, FAILED
    }
}
