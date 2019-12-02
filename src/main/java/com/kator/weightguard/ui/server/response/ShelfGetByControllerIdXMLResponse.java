package com.kator.weightguard.ui.server.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kator.weightguard.ui.entity.Shelf;
import com.kator.weightguard.ui.entity.Shelfs;

import java.util.List;

@JacksonXmlRootElement(localName = "reply")
public class ShelfGetByControllerIdXMLResponse {
    @JacksonXmlProperty(localName = "shelf_get_all_by_controller_id")
    private Shelfs shelfs;

    public ShelfGetByControllerIdXMLResponse() {
    }

    public ShelfGetByControllerIdXMLResponse(Shelfs shelfs) {
        this.shelfs = shelfs;
    }

    public List<Shelf> getShelfs() { return shelfs.getShelfs(); }

    public void setShelfs(Shelfs shelfs) { this.shelfs = shelfs; }

    @Override
    public String toString() {
        return "ShelfGetByControllerIdXMLResponse {" + "shelfs=" + shelfs.toString() + "}";
    }
}
