package com.kator.weightguard.ui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.Alarm;
import com.kator.weightguard.ui.server.request.AlarmGetAllRequest;
import com.kator.weightguard.ui.server.request.DeleteVideoRequest;
import com.kator.weightguard.ui.server.response.AlarmGetAllXMLResponse;
import com.kator.weightguard.ui.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by prats on 3/11/18.
 */
@Service
public class AlarmServiceImpl implements AlarmService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Alarm> getAll() {
        try {
            String xmlResponse = new AlarmGetAllRequest().send();

            AlarmGetAllXMLResponse response = objectMapper.readValue(xmlResponse, AlarmGetAllXMLResponse.class);

            return response.getAlarms();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }
}
