package com.knu.tubetalk.dto;

import java.time.LocalDateTime;

public class CommentView {
    private String id;          // 댓글ID 또는 답글ID
    private String type;        // "COMMENT" 또는 "REPLY"
    private String parentId;    // 부모 댓글 ID (답글일 경우 부모 ID, 댓글일 경우 본인 ID)
    private String content;
    private String userId;
    private String loginId;
    private LocalDateTime createdAt;
    private long likeCount;
    private long dislikeCount;

    public CommentView(String id, String type, String parentId, String content, 
                          String userId, String loginId, LocalDateTime createdAt, 
                          long likeCount, long dislikeCount) {
        this.id = id;
        this.type = type;
        this.parentId = parentId;
        this.content = content;
        this.userId = userId;
        this.loginId = loginId;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    // Getters
    public String getId() { return id; }
    public String getType() { return type; }
    public String getParentId() { return parentId; }
    public String getContent() { return content; }
    public String getUserId() { return userId; }
    public String getLoginId() { return loginId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public long getLikeCount() { return likeCount; }
    public long getDislikeCount() { return dislikeCount; }
}