package com.kator.weightguard.ui.service.impl;

import com.kator.weightguard.ui.entity.ServerSettings;
import com.kator.weightguard.ui.server.request.VideoServerSetParameterRequest;
import com.kator.weightguard.ui.server.request.WeightServerSetParameterRequest;
import com.kator.weightguard.ui.service.WeightServerSettingsService;
import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.kator.weightguard.ui.strings.Message.CAN_NOT_SAVE_PARAMETERS;
import static com.kator.weightguard.ui.strings.VideoConfigs.*;

@Service
public class WeightServerSettingsServiceImpl implements WeightServerSettingsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${weightserver.ini.file.location}")
    private String weightServerSettingsFile;

    @Override
    public ServerSettings getFromIniFile(){
        ServerSettings videoSettings = new ServerSettings();
        Ini iniWeightServer = new Ini();
        log.trace(">> getFromIniFile()");

        try {
            iniWeightServer.load(getSourceIniFile(weightServerSettingsFile));
        } catch (Exception e){
            log.error("Can not read parametrs from ini file");
            return videoSettings;
        }
        videoSettings.setVideoStorePeriod(iniWeightServer.get(VIDEO_CONFIG_SECTION, VIDEO_STORE_PERIOD));
        log.trace("<< getFromIniFile()");
        return videoSettings;
    }

    @Override
    public void sendToServer(ServerSettings videoSettings) throws Exception{
        String xmlResponse;

        if(!videoSettings.getVideoRecordFolder().isEmpty()) {
            xmlResponse = new WeightServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_RECORD_FOLDER, videoSettings.getVideoRecordFolder()).send();
            checkStatusOfSaving(xmlResponse);
        }

        if(!videoSettings.getVideoStorePeriod().isEmpty()) {
            xmlResponse = new WeightServerSetParameterRequest(VIDEO_CONFIG_SECTION, VIDEO_STORE_PERIOD, videoSettings.getVideoStorePeriod()).send();
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
