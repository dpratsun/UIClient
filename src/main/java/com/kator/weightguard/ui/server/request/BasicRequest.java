package com.kator.weightguard.ui.server.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by prats on 3/10/18.
 */
public abstract class BasicRequest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected String URL = "http://127.0.0.1:32323/";
    //protected String URL = "http://localhost/test.php";
    protected RestTemplate restTemplate = new RestTemplate();
    protected HttpEntity<String> entity;
    protected HttpHeaders headers = new HttpHeaders();
    protected HttpMethod httpMethod;

    public BasicRequest(String body) {
        headers.setContentType(MediaType.TEXT_PLAIN);
        entity = new HttpEntity<>(body, headers);
        httpMethod = HttpMethod.POST;
    }

    public String send() {
        try {
            log.trace("Request: " + entity.getBody());

            ResponseEntity<String> result = restTemplate.exchange(URL, httpMethod, entity, String.class);

            log.trace("Response: " + result.getBody());

            return result.getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new String();
    }
}
