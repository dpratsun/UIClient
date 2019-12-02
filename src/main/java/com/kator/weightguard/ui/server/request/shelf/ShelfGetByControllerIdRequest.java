package com.kator.weightguard.ui.server.request.shelf;

import com.kator.weightguard.ui.server.request.BasicRequest;

public class ShelfGetByControllerIdRequest extends BasicRequest {

    public ShelfGetByControllerIdRequest(String controllerId) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<shelf_get_all_by_controller_id version=\"1\">" +
                        "<shelf_controller_id>" +
                            controllerId +
                        "</shelf_controller_id>" +
                    "</shelf_get_all_by_controller_id>" +
                "</request>");
    }
}
