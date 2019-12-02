package com.kator.weightguard.ui.server.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kator.weightguard.ui.entity.ShelfError;
import com.kator.weightguard.ui.entity.ShelfErrors;

import java.util.List;

/**
 * Created by prats on 3/14/18.
 */
@JacksonXmlRootElement(localName = "reply")
public class ShelfErrorGetAllXMLResponse {

    @JacksonXmlProperty(localName = "shelf_error_get_all")
    private ShelfErrors shelfErrors;

    public ShelfErrorGetAllXMLResponse() {
    }

    public ShelfErrorGetAllXMLResponse(ShelfErrors shelfErrors) {
        this.shelfErrors = shelfErrors;
    }

    public List<ShelfError> getShelfErrors() {
        return shelfErrors.getShelfErrors();
    }

    public void setShelfErrors(ShelfErrors shelfErrors) {
        this.shelfErrors = shelfErrors;
    }

    @Override
    public String toString() {
        return "ShelfErrorGetAllXMLResponse{" +
                    "shelfErrors=" + shelfErrors.toString() +
                "}";
    }
}
