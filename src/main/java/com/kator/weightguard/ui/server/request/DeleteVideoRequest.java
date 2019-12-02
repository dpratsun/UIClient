package com.kator.weightguard.ui.server.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by prats on 3/13/18.
 */
public class DeleteVideoRequest extends BasicRequest {

    public DeleteVideoRequest(Integer videoId) {
        super("<?xml version=\"1.0\"?>" +
                "<request>" +
                    "<video_remove>" +
                        "<video>" +
                            "<id>" +
                                Integer.toString(videoId) +
                            "</id>" +
                        "</video>" +
                    "</video_remove>" +
                "</request>");
    }
}
