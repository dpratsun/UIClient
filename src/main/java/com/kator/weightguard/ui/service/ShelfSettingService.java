package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.entity.ShelfSetting;

import java.util.List;

public interface ShelfSettingService {
    List<ShelfSetting> getAll(String destinationIpAddr);
    void sendAll(String destinationIpAddr, List<ShelfSetting> shelfSettingsList);
}
