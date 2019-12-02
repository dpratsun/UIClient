package com.kator.weightguard.ui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.Shelf;
import com.kator.weightguard.ui.server.request.shelf.ShelfGetAllRequest;
import com.kator.weightguard.ui.server.request.shelf.ShelfGetByControllerIdRequest;
import com.kator.weightguard.ui.server.request.shelf.ShelfResetRequest;
import com.kator.weightguard.ui.server.request.shelf.ShelfUpdateRequest;
import com.kator.weightguard.ui.server.response.ShelfGetAllXMLResponse;
import com.kator.weightguard.ui.server.response.ShelfGetByControllerIdXMLResponse;
import com.kator.weightguard.ui.service.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by prats on 3/8/18.
 */
@Service
public class ShelfServiceImpl implements ShelfService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Shelf> getAll() {
        try {
            String xmlResult = new ShelfGetAllRequest().send();

            ShelfGetAllXMLResponse response = objectMapper.readValue(xmlResult, ShelfGetAllXMLResponse.class);

            return response.getShelfs();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    @Override
    public Boolean resetShelf(String shelfId) {
        try {
            new ShelfResetRequest(shelfId).send();

            return true;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }

    @Override
    public List<Shelf> getShelfByControllerId(String controllerId) {

        try {
            String xmlResult = new ShelfGetByControllerIdRequest(controllerId).send();

            ShelfGetByControllerIdXMLResponse response = objectMapper.readValue(xmlResult, ShelfGetByControllerIdXMLResponse.class);

            return response.getShelfs();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    @Override
    public Boolean shelfUpdate(String shelfId, String shelfTitle) {
        try {
            new ShelfUpdateRequest(shelfId, shelfTitle).send();

            return true;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }
}
