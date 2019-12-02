package com.kator.weightguard.ui.server.request;

public class VideoServerSetRecordDelayRequest extends BasicRequest {
    public VideoServerSetRecordDelayRequest(String value) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<set_record_delay_parameter version=\"1\">" +
                        "<value>" +
                            value +
                        "</value>" +
                    "</set_record_delay_parameter>" +
                "</request>");
        URL = "http://127.0.0.1:43434/";
    }
}
