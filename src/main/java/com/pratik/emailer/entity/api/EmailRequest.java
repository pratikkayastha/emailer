package com.pratik.emailer.entity.api;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmailRequest {

    private List<String> recipients;
    private String subject;
    private String content;

    private List<String> ccRecipients;
    private List<String> bccRecipients;

    private List<String> validationErrors;


    /**
     * Instead of using spring-boot-starter-data-jpa's annotation for validation,
     * I wrote this simple method as there are just few fields to validate
     * */
    public boolean isValid() {
        this.validationErrors = new ArrayList<>();
        Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (this.recipients!=null && !this.recipients.isEmpty()) {
            if (this.recipients.stream().filter(e -> emailRegex.matcher(e).matches()).count()!=this.recipients.size()) {
                this.validationErrors.add("Recipient email(s) are not valid");
            }
        } else {
            this.validationErrors.add("Recipient email missing");
        }

        if (StringUtils.isEmpty(this.subject)) {
            this.validationErrors.add("Subject is invalid");
        }
        if (StringUtils.isEmpty(this.content)) {
            this.validationErrors.add("Content is invalid");
        }

        if (this.ccRecipients!=null && !this.ccRecipients.isEmpty()) {
            if (this.ccRecipients.stream().filter(e -> emailRegex.matcher(e).matches()).count()!=this.ccRecipients.size()) {
                this.validationErrors.add("CC Recipient email(s) are not valid");
            }
        }

        if (this.bccRecipients!=null && !this.bccRecipients.isEmpty()) {
            if (this.bccRecipients.stream().filter(e -> emailRegex.matcher(e).matches()).count()!=this.bccRecipients.size()) {
                this.validationErrors.add("BCC Recipient email(s) are not valid");
            }
        }

        return this.validationErrors.size()==0;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public List<String> getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(List<String> ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    public List<String> getBccRecipients() {
        return bccRecipients;
    }

    public void setBccRecipients(List<String> bccRecipients) {
        this.bccRecipients = bccRecipients;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
