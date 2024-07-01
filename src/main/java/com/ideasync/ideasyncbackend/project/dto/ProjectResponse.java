package com.ideasync.ideasyncbackend.project.dto;

import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import com.ideasync.ideasyncbackend.comment.dto.CommentChunk;

import java.sql.Timestamp;
import java.util.List;

public class ProjectResponse {
    private Long id;
    private UserResponse hostUser;
    private Long statusId;
    private String title;
    private String description;
    private String school;
    private int allowApplicantsNum;
    private int applicantCount;
    private boolean isGraduationProject;
    private Timestamp createAt;
    private List<String> images;
    private List<String> tags;
    private String requireSkills;
    private List<UserResponse> applicants;
    private List<CommentChunk> commentChunks;

    public ProjectResponse(Long id,
                           UserResponse hostUser,
                           Long statusId,
                           String title,
                           String description,
                           String school,
                           int allowApplicantsNum,
                           int applicantCount,
                           boolean isGraduationProject,
                           Timestamp createAt,
                           List<String> images,
                           List<String> tags,
                           String requireSkills,
                           List<UserResponse> applicants,
                           List<CommentChunk> commentChunks) {
        this.id = id;
        this.hostUser = hostUser;
        this.statusId = statusId;
        this.title = title;
        this.description = description;
        this.school = school;
        this.allowApplicantsNum = allowApplicantsNum;
        this.applicantCount = applicantCount;
        this.isGraduationProject = isGraduationProject;
        this.createAt = createAt;
        this.images = images;
        this.tags = tags;
        this.requireSkills = requireSkills;
        this.applicants = applicants;
        this.commentChunks = commentChunks;
    }

    public String getRequireSkills() {
        return requireSkills;
    }

    public void setRequireSkills(String requireSkills) {
        this.requireSkills = requireSkills;
    }

    public UserResponse getHostUser() {
        return hostUser;
    }

    public void setHostUser(UserResponse hostUser) {
        this.hostUser = hostUser;
    }

    public List<UserResponse> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<UserResponse> applicants) {
        this.applicants = applicants;
    }

    public List<CommentChunk> getCommentChunks() {
        return commentChunks;
    }

    public void setCommentChunks(List<CommentChunk> commentChunks) {
        this.commentChunks = commentChunks;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public boolean isGraduationProject() {
        return isGraduationProject;
    }

    public int getApplicantCount() {
        return applicantCount;
    }

    public int getAllowApplicantsNum() {
        return allowApplicantsNum;
    }

    public String getSchool() {
        return school;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Long getStatusId() {
        return statusId;
    }

    public Long getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setAllowApplicantsNum(int allowApplicantsNum) {
        this.allowApplicantsNum = allowApplicantsNum;
    }

    public void setApplicantCount(int applicantCount) {
        this.applicantCount = applicantCount;
    }

    public void setGraduationProject(boolean graduationProject) {
        isGraduationProject = graduationProject;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
