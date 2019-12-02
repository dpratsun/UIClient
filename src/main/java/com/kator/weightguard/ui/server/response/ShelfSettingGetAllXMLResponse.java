package com.kator.weightguard.ui.server.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kator.weightguard.ui.entity.ShelfSetting;
import com.kator.weightguard.ui.entity.ShelfSettings;

import java.util.List;

@JacksonXmlRootElement(localName = "form")
public class ShelfSettingGetAllXMLResponse {
    @JacksonXmlProperty(localName = "shelfsettings_get_all")
    private ShelfSettings shelfSettings;

    public ShelfSettingGetAllXMLResponse() {
    }

    public ShelfSettingGetAllXMLResponse(ShelfSettings shelfSettings) {
        this.shelfSettings = shelfSettings;
    }

    public List<ShelfSetting> getShelfSettings() { return shelfSettings.getShelfSettings(); }

    public void setEndpoints(ShelfSettings shelfSettings) { this.shelfSettings = shelfSettings; }

    @Override
    public String toString() {
        return "ShelfSettingGetAllXMLResponse {" + "ShelfSettings=" + shelfSettings.toString() + "}";
    }
}
