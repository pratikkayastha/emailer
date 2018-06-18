package com.pratik.emailer.entity.sendgrid.request;

import com.pratik.emailer.entity.api.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendgridApiRequestMapper {

    @Value("${sender.fromEmail}")
    private String emailSender;

    public SendgridApiRequest from(EmailRequest emailRequest) {
        SendgridApiRequest sendgridApiRequest = new SendgridApiRequest();

        Personalization personalization = new Personalization();
        personalization.setTo(buildRecipients(emailRequest.getRecipients()));
        personalization.setCc(buildRecipients(emailRequest.getCcRecipients()));
        personalization.setBcc(buildRecipients(emailRequest.getBccRecipients()));

        List<Personalization> personalizations = new ArrayList<>();
        personalizations.add(personalization);
        sendgridApiRequest.setPersonalizations(personalizations);

        sendgridApiRequest.setFrom(new Email(emailSender));
        sendgridApiRequest.setSubject(emailRequest.getSubject());

        List<Content> contents = new ArrayList<>();
        // Content type will always be text/plain in this case
        contents.add(new Content("text/plain", emailRequest.getContent()));
        sendgridApiRequest.setContent(contents);

        return sendgridApiRequest;
    }

    private List<Email> buildRecipients(List<String> recipients) {
        if (recipients!=null && !recipients.isEmpty()) {
            List<Email> emailRecipient = new ArrayList<>();
            for (String email : recipients) {
                emailRecipient.add(new Email(email));
            }

            return emailRecipient;
        }

        return null;
    }
}
