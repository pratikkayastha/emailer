package com.pratik.emailer.sender;

import com.pratik.emailer.entity.api.EmailRequest;
import com.pratik.emailer.entity.api.EmailResponse;
import com.pratik.emailer.entity.sendgrid.request.SendgridApiRequest;
import com.pratik.emailer.entity.sendgrid.request.SendgridApiRequestMapper;
import com.pratik.emailer.service.HttpRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 0)   // Since this order is lower, this sender will be called first
public class SendgridSender implements ISender {

    private static final Logger log = LogManager.getLogger();

    @Value("${sender.sendgrid.accessKey}")
    private String accessKey;
    @Value("${sender.fromEmail}")
    private String sender;
    @Value("${sender.sendgrid.apiUrl}")
    private String apiUrl;

    @Autowired
    SendgridApiRequestMapper sendgridApiRequestMapper;
    @Autowired
    HttpRequestService httpRequestService;

    @Override
    public EmailResponse send(EmailRequest emailRequest) {
        log.debug("Building API request for Sendgrid...");

        String authHeader = "Bearer " + accessKey;
        SendgridApiRequest sendgridApiRequest = sendgridApiRequestMapper.from(emailRequest);
        int responseCode = httpRequestService.sendJsonPostRequest(apiUrl, authHeader, sendgridApiRequest);

        if (responseCode==202) {
            //Success
            return buildResponse(EmailResponse.StatusCode.OK);
        } else {
            //failure
            return buildResponse(EmailResponse.StatusCode.FAILED);
        }
    }

    @Override
    public String getGatewayName() {
        return "Sendgrid";
    }

    /**
     * Sample Request
     * url --MailgunRequest POST \
     --url https://api.sendgrid.com/v3/mail/send \
     --header 'Authorization: Bearer YOUR_API_KEY' \
     --header 'Content-Type: application/json' \
     --data '{"personalizations": [{"to": [{"email": "example@example.com"}]}],"from": {"email": "example@example.com"},
                "subject": "Hello, World!","content": [{"type": "text/plain", "value": "Heya!"}]}'
     * */
}
