package com.myweather.app.badmintonversion.entity;

/**
 * Created by zyt on 2017/12/6.
 */

public class VideoItem {
    private String videoTitle;
    private String videoUrl;
    private String videoCoverUrl;

    public VideoItem(String videoTitle, String videoUrl, String videoCoverUrl) {
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
        this.videoCoverUrl = videoCoverUrl;
    }

    public VideoItem() {
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoCoverUrl() {
        return videoCoverUrl;
    }

    public void setVideoCoverUrl(String videoCoverUrl) {
        this.videoCoverUrl = videoCoverUrl;
    }
}
