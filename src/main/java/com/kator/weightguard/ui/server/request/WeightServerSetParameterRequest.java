package com.kator.weightguard.ui.server.request;

public class WeightServerSetParameterRequest extends BasicRequest{
    public WeightServerSetParameterRequest(String section, String parameter, String value) {
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
    }
}
