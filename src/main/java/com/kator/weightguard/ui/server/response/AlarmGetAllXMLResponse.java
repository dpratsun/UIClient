package com.kator.weightguard.ui.server.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kator.weightguard.ui.entity.Alarm;
import com.kator.weightguard.ui.entity.Alarms;
import com.kator.weightguard.ui.entity.Shelf;
import com.kator.weightguard.ui.entity.Shelfs;

import java.util.List;

/**
 * Created by prats on 3/11/18.
 */
@JacksonXmlRootElement(localName = "reply")
public class AlarmGetAllXMLResponse {
    @JacksonXmlProperty(localName = "alarm_get_all")
    private Alarms alarms;

    public AlarmGetAllXMLResponse() {
    }

    public AlarmGetAllXMLResponse(Alarms alarms) {
        this.alarms = alarms;
    }

    public List<Alarm> getAlarms() { return alarms.getAlarms(); }

    public void setAlarms(Alarms alarms) { this.alarms = alarms; }

    @Override
    public String toString() {
        return "AlarmGetAllXMLResponse {" + "alarms=" + alarms.toString() + "}";
    }
}
