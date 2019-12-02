package com.kator.weightguard.ui.server.request;

public class VideoServerSetParameterRequest extends BasicRequest {
    public VideoServerSetParameterRequest(String section, String parameter, String value) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<set_parameter version=\"1\">" +
                        "<section>" +
                            section +
                        "</section>" +
                        "<parameter>" +
                            parameter +
                        "</parameter>" +
                        "<value>" +
                            value +
                        "</value>" +
                    "</set_parameter>" +
                "</request>");
        URL = "http://127.0.0.1:43434/";
    }
}
