package com.kator.weightguard.ui.service.impl;

import com.kator.weightguard.ui.entity.ServerSettings;
import com.kator.weightguard.ui.server.request.VideoServerSetParameterRequest;
import com.kator.weightguard.ui.server.request.VideoServerSetRecordDelayRequest;

import com.kator.weightguard.ui.service.VideoServerSettingsService;
import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;


import static com.kator.weightguard.ui.strings.Message.CAN_NOT_SAVE_PARAMETERS;
import static com.kator.weightguard.ui.strings.VideoConfigs.*;

@Service
public class VideoServerSettingsServiceImpl implements VideoServerSettingsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${videoserver.ini.file.location}")
    private String videoServerSettingsFile;

    @Override
    public ServerSettings getFromIniFile(){
        ServerSettings videoSettings = new ServerSettings();
        Ini iniVideoServer = new Ini();
        log.trace(">> getFromIniFile()");

        try {
            iniVideoServer.load(getSourceIniFile(videoServerSettingsFile));
        } catch (Exception e){
            log.error("Can not read parametrs from ini file");
            return videoSettings;
        }
        videoSettings.setVideoRecordFolder(iniVideoServer.get(VIDEO_CONFIG_SECTION, VIDEO_RECORD_FOLDER));
        videoSettings.setVideoRecordDuration(iniVideoServer.get(VIDEO_CONFIG_SECTION, VIDEO_RECORD_DURATION));
        videoSettings.setVideoRecordDelay(iniVideoServer.get(VIDEO_CONFIG_SECTION, VIDEO_RECORD_DELAY));
        videoSettings.setVideoViewDuration(iniVideoServer.get(VIDEO_CONFIG_SECTION, VIDEO_VIEW_DURATION));
        videoSettings.setVideoPartFrameWidht(iniVideoServer.get(VIDEO_CONFIG_SECTION, VIDEO_PART_FRAME_WIDTH));
        videoSettings.setVideoPartFrameHeight(iniVideoServer.get(VIDEO_CONFIG_SECTION, VIDEO_PART_FRAME_HEIGHT));
        log.trace("<< getFromIniFile()");
        return videoSettings;
    }

    @Override
    public void sendToServer(ServerSettings videoSettings) throws Exception{
        String xmlResponse;

        if(!videoSettings.getVideoRecordFolder().isEmpty()) {
            xmlResponse = new VideoServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_RECORD_FOLDER, videoSettings.getVideoRecordFolder()).send();
            checkStatusOfSaving(xmlResponse);
        }


        if(!videoSettings.getVideoRecordDuration().isEmpty()) {
            xmlResponse = new VideoServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_RECORD_DURATION, videoSettings.getVideoRecordDuration()).send();
            checkStatusOfSaving(xmlResponse);
        }

        if(!videoSettings.getVideoViewDuration().isEmpty()) {
            xmlResponse = new VideoServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_VIEW_DURATION, videoSettings.getVideoViewDuration()).send();
            checkStatusOfSaving(xmlResponse);
        }

        if(!videoSettings.getVideoPartFrameWidht().isEmpty()) {
            xmlResponse = new VideoServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_PART_FRAME_WIDTH, videoSettings.getVideoPartFrameWidht()).send();
            checkStatusOfSaving(xmlResponse);
        }

        if(!videoSettings.getVideoPartFrameHeight().isEmpty()) {
            xmlResponse = new VideoServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_PART_FRAME_HEIGHT, videoSettings.getVideoPartFrameHeight()).send();
            checkStatusOfSaving(xmlResponse);
        }

        if(!videoSettings.getVideoRecordDelay().isEmpty()) {
            xmlResponse = new VideoServerSetRecordDelayRequest(videoSettings.getVideoRecordDelay()).send();
            checkStatusOfSaving(xmlResponse);
        }
    }

    private File getSourceIniFile(String url) throws Exception{
        File sourceFile = new File(url);
        if(sourceFile.exists()) return sourceFile;;

        throw new Exception();
    }

    private void checkStatusOfSaving(String response) throws Exception {

        if (response.indexOf("<result>1</result>") == -1) {
            throw new Exception(CAN_NOT_SAVE_PARAMETERS);
        }
    }
}
