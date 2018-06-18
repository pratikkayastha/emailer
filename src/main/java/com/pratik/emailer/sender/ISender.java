package com.pratik.emailer.sender;

import com.pratik.emailer.entity.api.EmailRequest;
import com.pratik.emailer.entity.api.EmailResponse;

/**
 * Abstraction for email senders
 * Emails senders are Mailgun and Sendgrid
 * Implementation of this interface are responsible for sending
 * MailgunRequest to their respective senders
 */

public interface ISender {

    /**
     * SendgridApiRequest to be sent to actual email senders like Mailgun or sendgrid
     * @param emailRequest  Object containing details about email to be sent
     * @return		 	    A transaction attempt detailing the result of the payment MailgunRequest
     */
    EmailResponse send(EmailRequest emailRequest);

    /**
     * Returns gateway name, Sendgrid or Mailgun
     * Used to identifiy which gateway was used to send mail
     */
    String getGatewayName();

    default EmailResponse buildResponse(EmailResponse.StatusCode statusCode) {
        return new EmailResponse(statusCode,
                statusCode.equals(EmailResponse.StatusCode.OK) ? "Email send successfully via " + getGatewayName() :
                        "Email send failed for " + getGatewayName());
    }

}
