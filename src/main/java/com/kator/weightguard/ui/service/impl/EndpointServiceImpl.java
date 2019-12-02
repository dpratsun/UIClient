package com.kator.weightguard.ui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.Endpoint;
import com.kator.weightguard.ui.server.request.EndpointGetAllRequest;
import com.kator.weightguard.ui.server.response.EndpointGetAllXMLResponse;
import com.kator.weightguard.ui.service.EndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EndpointServiceImpl  implements EndpointService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Endpoint> getAll() {
        try {
            String xmlResponse = new EndpointGetAllRequest().send();

            EndpointGetAllXMLResponse response = objectMapper.readValue(xmlResponse, EndpointGetAllXMLResponse.class);

            return response.getEndpoints();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }
}
