package com.kator.weightguard.ui.server.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kator.weightguard.ui.entity.Endpoint;
import com.kator.weightguard.ui.entity.Endpoints;

import java.util.List;

@JacksonXmlRootElement(localName = "reply")
public class EndpointGetAllXMLResponse {
    @JacksonXmlProperty(localName = "endpoint_get_all")
    private Endpoints endpoints;

    public EndpointGetAllXMLResponse() {
    }

    public EndpointGetAllXMLResponse(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public List<Endpoint> getEndpoints() { return endpoints.getEndpoints(); }

    public void setEndpoints(Endpoints endpoints) { this.endpoints = endpoints; }

    @Override
    public String toString() {
        return "EndpointGetAllXMLResponse {" + "endpoints=" + endpoints.toString() + "}";
    }
}
