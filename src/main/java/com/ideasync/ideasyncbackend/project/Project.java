package com.ideasync.ideasyncbackend.project;
import com.ideasync.ideasyncbackend.applicant.Applicant;
import com.ideasync.ideasyncbackend.comment.Comment;
import com.ideasync.ideasyncbackend.projectimage.ProjectImage;
import com.ideasync.ideasyncbackend.projectstatus.ProjectStatus;
import com.ideasync.ideasyncbackend.tag.Tag;
import com.ideasync.ideasyncbackend.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "hostUserId", nullable = false)
  private User user;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "statusId", nullable = false)
  private ProjectStatus projectStatus;

  @Column(name = "isGraduationProject", nullable = false)
  private boolean isGraduationProject;

  @Column(name = "requireSkills", nullable = false)
  private String requireSkills;

  @Column(nullable = false)
  private String school;

  @Column(name = "allowApplicantsNum", nullable = false)
  private int allowApplicantsNum;

  @Column(name = "applicantCount", nullable = false)
  private int applicantCount;

  @CreationTimestamp
  @Column(name = "createAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createAt;

  @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
  private List<ProjectImage> projectImages;

  @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
  private List<Tag> tags;

  @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
  private List<Comment> comments;

  @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
  private List<Applicant> applicants;

  public String getRequireSkills() {
    return requireSkills;
  }

    public void setRequireSkills(String requireSkills) {
        this.requireSkills = requireSkills;
    }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public Timestamp getCreateAt() {
    return createAt;
  }

  public User getUser() {
    return user;
  }

  public String getDescription() {
    return description;
  }

  public ProjectStatus getProjectStatus() {
    return projectStatus;
  }

  public boolean isGraduationProject() {
    return isGraduationProject;
  }

  public String getSchool() {
    return school;
  }

  public int getAllowApplicantsNum() {
    return allowApplicantsNum;
  }

  public int getApplicantCount() {
    return applicantCount;
  }

  public List<ProjectImage> getProjectImages() {
    return projectImages;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public List<Applicant> getApplicants() {
    return applicants;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCreateAt(Timestamp createAt) {
    this.createAt = createAt;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setProjectStatus(ProjectStatus projectStatus) {
    this.projectStatus = projectStatus;
  }

  public void setGraduationProject(boolean graduationProject) {
    isGraduationProject = graduationProject;
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

  public void setProjectImages(List<ProjectImage> projectImages) {
    this.projectImages = projectImages;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void setApplicants(List<Applicant> applicants) {
    this.applicants = applicants;
  }
}

