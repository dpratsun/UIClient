package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.entity.ServerSettings;


public interface VideoServerSettingsService {
    ServerSettings getFromIniFile();
    void sendToServer(ServerSettings videoSettings) throws Exception;
}
