package com.pratik.emailer.entity.sendgrid.request;

import java.util.List;

public class Personalization {

    private List<Email> to;
    private List<Email> cc;
    private List<Email> bcc;

    public Personalization() {

    }

    public void addEmail(Email email) {
        this.to.add(email);
    }

    public List<Email> getTo() {
        return to;
    }

    public void setTo(List<Email> to) {
        this.to = to;
    }

    public List<Email> getCc() {
        return cc;
    }

    public void setCc(List<Email> cc) {
        this.cc = cc;
    }

    public List<Email> getBcc() {
        return bcc;
    }

    public void setBcc(List<Email> bcc) {
        this.bcc = bcc;
    }
}
