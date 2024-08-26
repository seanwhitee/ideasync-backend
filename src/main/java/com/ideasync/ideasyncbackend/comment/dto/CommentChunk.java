package com.ideasync.ideasyncbackend.comment.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class CommentChunk {
    private UUID id;
    private UUID userId;
    private UUID projectId;
    private String text;
    private List<CommentResponse> children;
    private String avatarURL;
    private String nickName;
    private Timestamp createAt;

    public CommentChunk(UUID id, UUID userId, UUID projectId, String text, List<CommentResponse> children, String avatarURL, String nickName, Timestamp createAt) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.text = text;
        this.children = children;
        this.avatarURL = avatarURL;
        this.nickName = nickName;
        this.createAt = createAt;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }


    public List<CommentResponse> getchildren() {
        return children;
    }

    public void setChildren(List<CommentResponse> children) {
        this.children = children;
    }
}
