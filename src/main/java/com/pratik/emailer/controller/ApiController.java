package com.pratik.emailer.controller;

import com.pratik.emailer.entity.api.EmailRequest;
import com.pratik.emailer.entity.api.EmailResponse;
import com.pratik.emailer.exception.InvalidEmailRequestException;
import com.pratik.emailer.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ApiController {

    @Autowired
    private EmailSenderService emailSenderService;

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {

        if (!emailRequest.isValid()) {
            throw new InvalidEmailRequestException(emailRequest.getValidationErrors());
        }

        EmailResponse emailResponse = emailSenderService.sendEmail(emailRequest);

        return new ResponseEntity<EmailResponse>(emailResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<EmailResponse> handleException(Exception ex) {
        EmailResponse errorResponse = new EmailResponse();
        errorResponse.setStatus(EmailResponse.StatusCode.FAILED);
        errorResponse.getMessage().add("Something went wrong!");
        errorResponse.getMessage().add(ex.toString());

        ex.printStackTrace();

        return new ResponseEntity<EmailResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidEmailRequestException.class)
    public final ResponseEntity<EmailResponse> handleIllegalArgumentException(final InvalidEmailRequestException e) {
        EmailResponse errorResponse = new EmailResponse();
        errorResponse.setStatus(EmailResponse.StatusCode.FAILED);
        errorResponse.getMessage().add("Validation Failed");
        e.getErrorMessages().forEach(err -> errorResponse.getMessage().add(err));

        return new ResponseEntity<EmailResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}