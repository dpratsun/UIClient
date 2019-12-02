package com.kator.weightguard.ui.service.impl;

import com.kator.weightguard.ui.server.request.DeleteVideoRequest;
import com.kator.weightguard.ui.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * Created by prats on 3/13/18.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public Boolean deleteVideo(Integer videoId) {
        String response = new DeleteVideoRequest(videoId).send();

        if (response == null) return false;

        return true;
    }
}
