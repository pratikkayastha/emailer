package com.pratik.emailer.service;

import com.pratik.emailer.entity.api.EmailRequest;
import com.pratik.emailer.entity.api.EmailResponse;
import com.pratik.emailer.sender.ISender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    List<ISender> emailSenders;

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        log.debug("Processing Email Request... ");

        EmailResponse emailResponse = new EmailResponse();
        for (ISender sender : emailSenders) {
            if (!emailResponse.getStatus().equals(EmailResponse.StatusCode.OK)) {
                log.debug("Trying to send email via " + sender.getGatewayName());

                emailResponse = sender.send(emailRequest);
                emailResponse.setEmailGateway(sender.getGatewayName());

                log.debug("Received " + emailResponse.getStatus() + " status for " + sender.getGatewayName());
            }
        }

        return emailResponse;
    }
}
