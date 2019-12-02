package com.kator.weightguard.ui.server.request.shelferror;

import com.kator.weightguard.ui.server.request.BasicRequest;

/**
 * Created by prats on 3/15/18.
 */
public class ShelfErrorSetCheckedRequest extends BasicRequest {
    public ShelfErrorSetCheckedRequest(Integer shelfErrorId) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<shelf_error_update>" +
                        "<shelf_error>" +
                            "<id>" +
                                Integer.toString(shelfErrorId) +
                            "</id>" +
                        "</shelf_error>" +
                    "</shelf_error_update>" +
                "</request>");

    }
}
