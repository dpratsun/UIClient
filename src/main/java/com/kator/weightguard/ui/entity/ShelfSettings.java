package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "shelfsettings_get_all")
public class ShelfSettings {
    @JacksonXmlProperty(localName = "shelfsettings")
    private List<ShelfSetting> shelfSettings = new ArrayList<>();

    public ShelfSettings() {}

    public ShelfSettings(List<ShelfSetting> shelfSettings) {
        this.shelfSettings = shelfSettings;
    }

    public List<ShelfSetting> getShelfSettings() { return shelfSettings; }

    public void setShelfSettings(List<ShelfSetting> shelfSettings) { this.shelfSettings = shelfSettings; }

    @Override
    public String toString() {
        String listString = shelfSettings.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "{" + listString + "}";
    }
}
