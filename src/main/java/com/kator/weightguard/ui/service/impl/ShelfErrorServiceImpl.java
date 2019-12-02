package com.kator.weightguard.ui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.ShelfError;
import com.kator.weightguard.ui.server.request.shelferror.ShelfErrorGetAllRequest;
import com.kator.weightguard.ui.server.request.shelferror.ShelfErrorSetCheckedRequest;
import com.kator.weightguard.ui.server.response.ShelfErrorGetAllXMLResponse;
import com.kator.weightguard.ui.service.ShelfErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by prats on 3/14/18.
 */
@Service
public class ShelfErrorServiceImpl implements ShelfErrorService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<ShelfError> getAll() {
        String xmlResponse = new ShelfErrorGetAllRequest().send();

        try {
            ShelfErrorGetAllXMLResponse response = objectMapper.readValue(xmlResponse, ShelfErrorGetAllXMLResponse.class);
            return response.getShelfErrors();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    @Override
    public Boolean setChecked(Integer shelfErrorId) {

        String response = new ShelfErrorSetCheckedRequest(shelfErrorId).send();

        if (response == null) return false;

        return true;
    }
}
