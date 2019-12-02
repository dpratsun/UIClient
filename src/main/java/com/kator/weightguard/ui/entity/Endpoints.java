package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "endpoint_get_all")
public class Endpoints {
    @JacksonXmlProperty(localName = "endpoint")
    private List<Endpoint> endpoints = new ArrayList<>();

    public Endpoints() {}

    public Endpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public List<Endpoint> getEndpoints() { return endpoints; }

    public void setEndpoints(List<Endpoint> endpoints) { this.endpoints = endpoints; }

    @Override
    public String toString() {
        String listString = endpoints.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "{" + listString + "}";
    }
}
