package com.kator.weightguard.ui.server.request;

import org.springframework.http.HttpMethod;

public class ShelfSettingGetAllRequest extends BasicRequest {

    public ShelfSettingGetAllRequest(String destinationIpAddr) {
        super("");
        URL = "http://" + destinationIpAddr + ":80/endpointSettings.cgx";
        httpMethod = HttpMethod.GET;
    }
}
