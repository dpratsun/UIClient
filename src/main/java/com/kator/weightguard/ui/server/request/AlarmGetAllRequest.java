package com.kator.weightguard.ui.server.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by prats on 3/11/18.
 */
public class AlarmGetAllRequest extends BasicRequest {

    public AlarmGetAllRequest() {
        super("<?xml version=\"1.0\"?><request><alarm_get_all></alarm_get_all></request>");
    }

}
