package com.kator.weightguard.ui.server.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kator.weightguard.ui.entity.Shelf;
import com.kator.weightguard.ui.entity.Shelfs;

import java.util.List;

/**
 * Created by prats on 3/10/18.
 */
@JacksonXmlRootElement(localName = "reply")
public class ShelfGetAllXMLResponse {

    @JacksonXmlProperty(localName = "shelf_get_all")
    private Shelfs shelfs;

    public ShelfGetAllXMLResponse() {
    }

    public ShelfGetAllXMLResponse(Shelfs shelfs) {
        this.shelfs = shelfs;
    }

    public List<Shelf> getShelfs() { return shelfs.getShelfs(); }

    public void setShelfs(Shelfs shelfs) { this.shelfs = shelfs; }

    @Override
    public String toString() {
        return "ShelfGetAllXMLResponse {" + "shelfs=" + shelfs.toString() + "}";
    }
}
