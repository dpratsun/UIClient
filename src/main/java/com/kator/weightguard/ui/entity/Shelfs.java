package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by prats on 3/10/18.
 */

public final class Shelfs {

    @JacksonXmlProperty(localName = "shelf")
    private List<Shelf> shelfs = new ArrayList<>();

    public Shelfs() {
    }

    public Shelfs(List<Shelf> shelfs) {
        this.shelfs = shelfs;
    }

    public List<Shelf> getShelfs() {
        return shelfs;
    }

    public void setShelfs(List<Shelf> shelfs) {
        this.shelfs = shelfs;
    }

    @Override
    public String toString() {
        String listString = shelfs.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "{" + listString + "}";
    }
}
