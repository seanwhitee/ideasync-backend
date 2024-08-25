package com.ideasync.ideasyncbackend.project.dto;

import com.ideasync.ideasyncbackend.applicant.dto.ApplicantResponse;
import com.ideasync.ideasyncbackend.user.dto.UserResponse;
import com.ideasync.ideasyncbackend.comment.dto.CommentChunk;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ProjectResponse {
    private UUID id;
    private UserResponse hostUser;
    private String status;
    private String title;
    private String description;
    private String school;
    private int allowApplicantsNum;
    private boolean isGraduationProject;
    private Timestamp createAt;
    private List<String> images;
    private List<String> tags;
    private String requireSkills;
    private List<ApplicantResponse> applicants;
    private List<CommentChunk> commentChunks;

    public ProjectResponse(UUID id,
                           UserResponse hostUser,
                           String status,
                           String title,
                           String description,
                           String school,
                           int allowApplicantsNum,
                           boolean isGraduationProject,
                           Timestamp createAt,
                           List<String> images,
                           List<String> tags,
                           String requireSkills,
                           List<ApplicantResponse> applicants,
                           List<CommentChunk> commentChunks) {
        this.id = id;
        this.hostUser = hostUser;
        this.status = status;
        this.title = title;
        this.description = description;
        this.school = school;
        this.allowApplicantsNum = allowApplicantsNum;
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

    public List<ApplicantResponse> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<ApplicantResponse> applicants) {
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

    public String getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStatusId(String status) {
        this.status = status;
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
