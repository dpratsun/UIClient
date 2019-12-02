package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Endpoint {
    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "ip")
    private String ip;

    public Endpoint(){
    }

    public Endpoint(String id, String ip){
        this.id = id;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) { this.ip = ip; }

    @Override
    public String toString() {
        return "Endpoint{" +
                "id=" + id +
                ", ip=" + ip +
                '}';
    }
}
