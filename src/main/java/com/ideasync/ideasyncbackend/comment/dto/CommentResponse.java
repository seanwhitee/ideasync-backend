package com.ideasync.ideasyncbackend.comment.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class CommentResponse {
    private UUID id;
    private String text;
    private UUID userId;
    private UUID projectId;
    private UUID parentId;
    private String avatarURL;
    private String nickName;
    private Timestamp createAt;

    public CommentResponse(UUID id, String text, UUID userId, UUID projectId, UUID parentId, String avatarURL, String nickName, Timestamp createAt) {
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.projectId = projectId;
        this.parentId = parentId;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
}
