package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ShelfSetting {
    @JacksonXmlProperty(localName = "shelf_id")
    private String shelfId;

    @JacksonXmlProperty(localName = "shelf_title")
    private String shelfTitle;

    @JacksonXmlProperty(localName = "weight_of_one_position")
    private String weightOfOnePosition;

    @JacksonXmlProperty(localName = "weight_difference")
    private String weightDifferense;

    @JacksonXmlProperty(localName = "event_alarm_difference")
    private String eventAlarmDifference;

    public ShelfSetting(){
    }

    public ShelfSetting(String shelfId, String shelfTitle, String weightOfOnePosition, String weightDifferense, String eventAlarmDifference) {
        this.shelfId = shelfId;
        this.shelfTitle = shelfTitle;
        this.weightOfOnePosition = weightOfOnePosition;
        this.weightDifferense = weightDifferense;
        this.eventAlarmDifference = eventAlarmDifference;
    }

    public String getId() {
        return shelfId;
    }

    public void setId(String shelfId) {
        this.shelfId = shelfId;
    }

    public String getTitle() {
        return shelfTitle;
    }

    public void setTitle(String shelfTitle) {
        this.shelfTitle = shelfTitle;
    }

    public String getWeightOfOnePosition() {
        return weightOfOnePosition;
    }

    public void setWeightOfOnePosition(String weightOfOnePosition) { this.weightOfOnePosition = weightOfOnePosition; }

    public String getWeightDifferense() {
        return weightDifferense;
    }

    public void setWeightDifferense(String weightDifferense) {
        this.weightDifferense = weightDifferense;
    }

    public String getEventAlarmDifference() { return eventAlarmDifference; }

    public void setEventAlarmDifference(String eventAlarmDifference) { this.eventAlarmDifference = eventAlarmDifference; }

    @Override
    public String toString() {
        return "ShelfSettings{" +
                "shelf_id=" + shelfId +
                "shelf_title=" + shelfTitle +
                ", weight_of_one_position=" + weightOfOnePosition +
                ", weight_difference=" + weightDifferense +
                ", event_alarm_difference=" + eventAlarmDifference +
                '}';
    }
}
