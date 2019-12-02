package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by prats on 3/11/18.
 */
@JacksonXmlRootElement(localName = "alarm_get_all")
public class Alarms {

    @JacksonXmlProperty(localName = "alarm")
    private List<Alarm> alarms = new ArrayList<>();

    public Alarms() {}

    public Alarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public List<Alarm> getAlarms() { return alarms; }

    public void setAlarms(List<Alarm> alarms) { this.alarms = alarms; }

    @Override
    public String toString() {
        String listString = alarms.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "{" + listString + "}";
    }
}
