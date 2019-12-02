package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by prats on 3/14/18.
 */
public class ShelfErrors {
    @JacksonXmlProperty(localName = "shelf_error")
    private List<ShelfError> shelfErrors = new ArrayList<>();

    public ShelfErrors() {
    }

    public ShelfErrors(List<ShelfError> shelfErrors) {
        this.shelfErrors = shelfErrors;
    }

    public List<ShelfError> getShelfErrors() {
        return shelfErrors;
    }

    public void setShelfErrors(List<ShelfError> shelfErrors) {
        this.shelfErrors = shelfErrors;
    }

    @Override
    public String toString() {
        String listString = shelfErrors.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "{" + listString + "}";
    }
}
