package com.knu.tubetalk.dto;

public class TrendingThread {
    private String videoId;
    private String title;
    private long commentCount;

    public TrendingThread(String videoId, String title, long commentCount) {
        this.videoId = videoId;
        this.title = title;
        this.commentCount = commentCount;
    }

    // Getters
    public String getVideoId() { return videoId; }
    public String getTitle() { return title; }
    public long getCommentCount() { return commentCount; }
}