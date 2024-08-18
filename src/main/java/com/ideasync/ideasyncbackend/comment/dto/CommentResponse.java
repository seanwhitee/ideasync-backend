package com.ideasync.ideasyncbackend.comment.dto;

import java.sql.Timestamp;

public class CommentResponse {
    private Long id;
    private String text;
    private Long userId;
    private Long projectId;
    private Long parentId;
    private String avatarURL;
    private String nickName;
    private Timestamp createAt;

    public CommentResponse(Long id, String text, Long userId, Long projectId, Long parentId, String avatarURL, String nickName, Timestamp createAt) {
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
