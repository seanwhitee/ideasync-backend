package com.ideasync.ideasyncbackend.project.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ProjectRequest {
  private UUID hostId;
  private String title;
  private String description;
  private String status;
  private String requireSkills;
  private boolean isGraduationProject;
  private String school;
  private int allowApplicantsNum;
  private Timestamp createAt;
  private List<String> projectImages;
  private List<String> tags;

  // constructor
  public ProjectRequest(UUID hostId,
                        String title,
                        String description,
                        String status,
                        boolean isGraduationProject,
                        String school,
                        int allowApplicantsNum,
                        Timestamp createAt,
                        List<String> projectImages,
                        List<String> tags,
                        String requireSkills) {
    this.hostId = hostId;
    this.title = title;
    this.description = description;
    this.status = status;
    this.isGraduationProject = isGraduationProject;
    this.school = school;
    this.allowApplicantsNum = allowApplicantsNum;
    this.createAt = createAt;
    this.projectImages = projectImages;
    this.tags = tags;
    this.requireSkills = requireSkills;
  }

  public String getRequireSkills() {
    return requireSkills;
  }

  public void setRequireSkills(String requireSkills) {
      this.requireSkills = requireSkills;
  }

  public UUID getHostId() {
    return hostId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public boolean getGraduationProject() {
    return isGraduationProject;
  }

  public String getSchool() {
    return school;
  }

  public int getAllowApplicantsNum() {
    return allowApplicantsNum;
  }

  public Timestamp getCreateAt() {
    return createAt;
  }

  public List<String> getProjectImages() {
    return projectImages;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setHostId(UUID hostId) {
    this.hostId = hostId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setGraduationProject(boolean isGraduationProject) {
    this.isGraduationProject = isGraduationProject;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public void setAllowApplicantsNum(int allowApplicantsNum) {
    this.allowApplicantsNum = allowApplicantsNum;
  }

  public void setCreateAt(Timestamp createAt) {
    this.createAt = createAt;
  }

  public void setProjectImages(List<String> projectImages) {
    this.projectImages = projectImages;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }
}
