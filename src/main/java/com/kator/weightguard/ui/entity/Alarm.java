package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by prats on 3/11/18.
 */
public class Alarm {
    @JacksonXmlProperty(localName = "id")
    private Integer id;
    @JacksonXmlProperty(localName = "date_time")
    private String date;
    @JacksonXmlProperty(localName = "shelf_id")
    private String shelfId;
    @JacksonXmlProperty(localName = "video_id")
    private Integer videoId;
    @JacksonXmlProperty(localName = "file_name")
    private String filePath;
    @JacksonXmlProperty(localName = "shelf_title")
    private String shelfTitle;

    public Alarm() {
    }

    public Alarm(Integer id, String date, String shelfId, Integer videoId, String filePath) {
        this.id = id;
        this.date = date;
        this.shelfId = shelfId;
        this.videoId = videoId;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getShelfTitle() {
        return shelfTitle;
    }

    public void setShelfTitle(String shelfTitle) {
        this.shelfTitle = shelfTitle;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", date=" + date +
                ", shelfId='" + shelfId + '\'' +
                ", videoId=" + videoId +
                ", filePath='" + filePath + '\'' +
                ", shelfTitle='" + shelfTitle + '\'' +
                '}';
    }
}
