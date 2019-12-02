package com.kator.weightguard.ui.server.request.shelf;

import com.kator.weightguard.ui.server.request.BasicRequest;

public class ShelfUpdateRequest extends BasicRequest {

    public ShelfUpdateRequest(String shelfId, String shelfTitle) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<shelf_update version=\"1\">" +
                        "<shelf>" +
                            "<id>" +
                                shelfId +
                            "</id>" +
                            "<title>" +
                                shelfTitle +
                            "</title>" +
                        "</shelf>" +
                    "</shelf_update>" +
                "</request>");
    }
}
