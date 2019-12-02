package com.kator.weightguard.ui.server.request.shelferror;

import com.kator.weightguard.ui.server.request.BasicRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by prats on 3/14/18.
 */
public class ShelfErrorGetAllRequest extends BasicRequest {

    public ShelfErrorGetAllRequest() {
        super("<?xml version=\"1.0\"?><request><shelf_error_get_all></shelf_error_get_all></request>");
    }
}
