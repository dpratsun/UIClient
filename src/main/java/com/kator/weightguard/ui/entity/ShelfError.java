package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by prats on 3/14/18.
 */
public class ShelfError {
    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JacksonXmlProperty(localName = "shelf_id")
    private String shelfId;

    @JacksonXmlProperty(localName = "code")
    private Integer code;

    @JacksonXmlProperty(localName = "date_time")
    private String date;

    public ShelfError() {
    }

    public ShelfError(Integer id, String shelfId, Integer code, String date) {
        this.id = id;
        this.shelfId = shelfId;
        this.code = code;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ShelfError{" +
                "id=" + id +
                ", shelfId='" + shelfId + '\'' +
                ", code=" + code +
                ", date='" + date + '\'' +
                '}';
    }
}
