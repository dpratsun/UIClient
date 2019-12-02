package com.kator.weightguard.ui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.ShelfSetting;
import com.kator.weightguard.ui.server.request.ShelfSettingGetAllRequest;
import com.kator.weightguard.ui.server.request.ShelfSettingSendAllRequest;
import com.kator.weightguard.ui.server.response.ShelfSettingGetAllXMLResponse;
import com.kator.weightguard.ui.service.ShelfSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ShelfSettingServiceImpl implements ShelfSettingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<ShelfSetting> getAll(String destinationIpAddr) {
        try {
            String xmlResponse = new ShelfSettingGetAllRequest(destinationIpAddr).send();

            ShelfSettingGetAllXMLResponse response = objectMapper.readValue(xmlResponse, ShelfSettingGetAllXMLResponse.class);

            return response.getShelfSettings();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    @Override
    public void sendAll(String destinationIpAddr, List<ShelfSetting> shelfSettingsList){
        try {
            new ShelfSettingSendAllRequest(destinationIpAddr, shelfSettingsList).send();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
