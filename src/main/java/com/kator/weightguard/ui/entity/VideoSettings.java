package com.kator.weightguard.ui.entity;

public final class VideoSettings {

    private String videoRecordFolder;

    private String videoRecordDuration;

    private String videoRecordDelay;

    private String videoViewDuration;

    private String videoStorePeriod;

    private String videoPartFrameWidht;

    private String videoPartFrameHeight;

    public VideoSettings(){
    }

    public VideoSettings(String videoRecordFolder, String videoRecordDuration, String videoRecordDelay, String videoViewDuration, String videoStorePeriod, String videoPartFrameWidth, String videoPartFrameHeight){
        this.videoRecordFolder = videoRecordFolder;
        this.videoRecordDuration = videoRecordDuration;
        this.videoRecordDelay = videoRecordDelay;
        this.videoViewDuration = videoViewDuration;
        this.videoStorePeriod = videoStorePeriod;
        this.videoPartFrameWidht = videoPartFrameWidth;
        this.videoPartFrameHeight = videoPartFrameHeight;
    }

    public String getVideoRecordFolder() { return videoRecordFolder; }

    public void setVideoRecordFolder(String videoRecordFolder) { this.videoRecordFolder = videoRecordFolder; }

    public String getVideoRecordDuration() {
        return videoRecordDuration;
    }

    public void setVideoRecordDuration(String videoRecordDuration) {
        this.videoRecordDuration = videoRecordDuration;
    }

    public String getVideoRecordDelay() {
        return videoRecordDelay;
    }

    public void setVideoRecordDelay(String videoRecordDelay) {
        this.videoRecordDelay = videoRecordDelay;
    }

    public String getVideoViewDuration() {
        return videoViewDuration;
    }

    public void setVideoViewDuration(String videoViewDuration) {
        this.videoViewDuration = videoViewDuration;
    }

    public String getVideoStorePeriod() { return videoStorePeriod; }

    public void setVideoStorePeriod(String videoStorePeriod) { this.videoStorePeriod = videoStorePeriod; }

    public String getVideoPartFrameWidht() { return videoPartFrameWidht; }

    public void setVideoPartFrameWidht(String videoPartFrameWidth) { this.videoPartFrameWidht = videoPartFrameWidth; }

    public String getVideoPartFrameHeight() { return videoPartFrameHeight; }

    public void setVideoPartFrameHeight(String videoPartFrameHeight) { this.videoPartFrameHeight = videoPartFrameHeight; }

    @Override
    public String toString() {
        return "VideoConfig{" +
                "videoRecordFolder=" + videoRecordFolder +
                ", videoRecordDuration=" + videoRecordDuration +
                ", videoRecordDelay=" + videoRecordDelay +
                ", videoViewDuration=" + videoViewDuration +
                ", videoStorePeriod=" + videoStorePeriod +
                ", videoPartFrameWidht=" + videoPartFrameWidht +
                ", videoPartFrameHeight=" + videoPartFrameHeight +
                '}';
    }
}
