package com.ideasync.ideasyncbackend.project.dto;

import java.sql.Timestamp;
import java.util.List;

public class ProjectRequest {
  private Long hostId;
  private String title;
  private String description;
  private Long statusId;
  private String requireSkills;
  private boolean isGraduationProject;
  private String school;
  private int allowApplicantsNum;
  private Timestamp createAt;
  private List<String> projectImages;
  private List<String> tags;

  // constructor
  public ProjectRequest(Long hostId,
                        String title,
                        String description,
                        Long statusId,
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
    this.statusId = statusId;
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

  public Long getHostId() {
    return hostId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Long getStatusId() {
    return statusId;
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

  public void setHostId(Long hostId) {
    this.hostId = hostId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStatusId(Long statusId) {
    this.statusId = statusId;
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
