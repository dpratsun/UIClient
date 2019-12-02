package com.kator.weightguard.ui.server.request.shelf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.Shelf;
import com.kator.weightguard.ui.server.request.BasicRequest;
import com.kator.weightguard.ui.server.response.ShelfGetAllXMLResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by prats on 3/10/18.
 */
public class ShelfGetAllRequest extends BasicRequest {

    public ShelfGetAllRequest() {
        super("<?xml version=\"1.0\"?><request><shelf_get_all></shelf_get_all></request>");
    }
}
