package com.pratik.emailer.sender;

import com.pratik.emailer.entity.api.EmailRequest;
import com.pratik.emailer.entity.api.EmailResponse;
import com.pratik.emailer.service.HttpRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Order(value = 1)   // Since this order is higher, this sender will be called second
public class MailgunSender implements ISender {

    private static final Logger log = LogManager.getLogger();

    @Value("${sender.mailgun.key}")
    private String apiKey;
    @Value("${sender.fromEmail}")
    private String emailSender;
    @Value("${sender.mailgun.apiUrl}")
    private String apiUrl;

    @Autowired
    HttpRequestService httpRequestService;

    @Override
    public EmailResponse send(EmailRequest emailRequest) {
        log.debug("Building API request for Mailgun...");

        String authHeader = "Basic " + Base64.getEncoder().encodeToString(("api:"+apiKey).getBytes());
        StringBuilder requestUrl = new StringBuilder();
        requestUrl.append(apiUrl).append("?");
        requestUrl.append("to=").append(String.join(",", emailRequest.getRecipients())).append("&");
        requestUrl.append("from=").append(emailSender).append("&");
        requestUrl.append("subject=").append(emailRequest.getSubject()).append("&");
        requestUrl.append("text=").append(emailRequest.getContent()).append("&");
        requestUrl.append("cc=").append(String.join(",", emailRequest.getCcRecipients())).append("&");
        requestUrl.append("bcc=").append(String.join(",", emailRequest.getBccRecipients())).append("&");

        int responseCode = httpRequestService.sendJsonPostRequest(requestUrl.toString(), authHeader);
        if (responseCode==200) {
            //Success
            return buildResponse(EmailResponse.StatusCode.OK);
        } else {
            //Failure
            return buildResponse(EmailResponse.StatusCode.FAILED);
        }
    }

    @Override
    public String getGatewayName() {
        return "Mailgun";
    }

    /**
     * Sample Request
     * https://api.mailgun.net/v3/sandboxf459fcdeb4774e65a8c0fe508f2241e5.mailgun.org/messages
     * ?from=a@a.com&to=pratik.kayastha@gmail.com&subject=This is subject&text=this is text
     * Authorization: Basic Base64 (api:key)
     */
}
