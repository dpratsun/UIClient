package com.kator.weightguard.ui.server.request.shelf;

import com.kator.weightguard.ui.server.request.BasicRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by prats on 3/11/18.
 */
public class ShelfResetRequest extends BasicRequest {

    public ShelfResetRequest(String shelfId) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<shelf_reset>" +
                        "<shelf>" +
                            "<id>" +
                                shelfId +
                            "</id>" +
                        "</shelf>" +
                    "</shelf_reset>" +
                "</request>");
     }
}
