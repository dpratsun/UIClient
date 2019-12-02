package com.kator.weightguard.ui.server.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class EndpointGetAllRequest extends BasicRequest {

    public EndpointGetAllRequest() {
        super("<?xml version=\"1.0\"?><request><endpoint_get_all version=\"1\"/></request>");
    }

}
