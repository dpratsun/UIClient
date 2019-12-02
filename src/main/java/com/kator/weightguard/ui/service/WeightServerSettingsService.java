package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.entity.ServerSettings;

public interface WeightServerSettingsService {
    ServerSettings getFromIniFile();
    void sendToServer(ServerSettings videoSettings) throws Exception;
}
