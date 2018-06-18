package com.pratik.emailer.service;

import com.pratik.emailer.entity.sendgrid.request.SendgridApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpRequestService {

    /**
     * TODO: Parse the response sent by each WS API
     * Current, I just check the response code; if response code = 2xx, its a success, otherwise its a failure
     * To parse the response, we will have to create POJO corresponding to the response and ask RestTemplate to parse the response
     * ResponseEntity<ResponsePOJO> response = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, ResponsePOJO.class);
     *
     * RestTemplate throws HttpStatusCodeException if status code != 2xx, so we will have to catch it and parse the response string
     * (the response is available as string if case of HttpStatusCodeException) to POJO using com.fasterxml.jackson.databind.ObjectMapper.
     * */

    private static final Logger log = LogManager.getLogger();

    @Value("${sender.timeout}")
    private Integer timeout;

    public int sendJsonPostRequest(String requestUrl, String authHeader, SendgridApiRequest sendgridApiRequest) {
        log.debug("Sending POST request using following params:");
        log.debug(requestUrl);
        log.debug(authHeader);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
            SimpleClientHttpRequestFactory rf = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
            rf.setReadTimeout(timeout);
            rf.setConnectTimeout(timeout);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", authHeader);

            HttpEntity<SendgridApiRequest> entity = new HttpEntity<SendgridApiRequest>(sendgridApiRequest, headers);
            ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, String.class);

            log.debug("Request returned " + response.getStatusCodeValue());

            return response.getStatusCodeValue();
        } catch (HttpStatusCodeException hce) {
            log.debug("Request failed and returned " + hce.getRawStatusCode());
            return hce.getRawStatusCode();
        }
    }

    public int sendJsonPostRequest(String requestUrl, String authHeader) {
        return sendJsonPostRequest(requestUrl, authHeader, null);
    }
}
