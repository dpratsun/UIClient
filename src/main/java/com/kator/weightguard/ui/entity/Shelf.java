package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by prats on 3/8/18.
 */
public final class Shelf {
    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "status")
    private Integer status;

    @JacksonXmlProperty(localName = "title")
    private String title;

    public Shelf() {
    }

    public Shelf(String id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", title='" + title + '\'' +
                '}';
    }
}
