package com.ideasync.ideasyncbackend.user;

import com.ideasync.ideasyncbackend.applicant.Applicant;
import com.ideasync.ideasyncbackend.comment.Comment;
import com.ideasync.ideasyncbackend.project.Project;
import com.ideasync.ideasyncbackend.userrole.UserRole;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "userName", nullable = false, unique = true)
  private String userName;

  @Column(nullable = false)
  private String password;

  @Column(name = "nickName", nullable = false)
  private String nickName;

  @Column(name = "profileDescription", nullable = false)
  private String profileDescription;

  @ManyToOne
  @JoinColumn(name = "roleId", nullable = false)
  private UserRole userRole;

  @Column(name = "allowProjectApply", nullable = false)
  private boolean allowProjectApply;

  @Column(name = "allowProjectCreate", nullable = false)
  private boolean allowProjectCreate;

  @Column(name = "roleVerified", nullable = false)
  private boolean roleVerified;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "emailVerified", nullable = false)
  private boolean emailVerified;

  @Column(name = "avatarUrl", nullable =  false)
  private String avatarUrl;

  @Column(name = "firstName", nullable = false)
  private String firstName;

  @Column(name = "lastName", nullable = false)
  private String lastName;

  @OneToMany(mappedBy = "user")
  private List<Project> projects;

  @OneToMany(mappedBy = "user")
  private List<Comment> comments;

  @OneToMany(mappedBy = "user")
  private List<Applicant> applicants;


  // Constructors, getters, and setters

  public Long getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getNickName() {
    return nickName;
  }

  public String getProfileDescription() {
    return profileDescription;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public boolean isAllowProjectApply() {
    return allowProjectApply;
  }

  public boolean isAllowProjectCreate() {
    return allowProjectCreate;
  }

  public boolean isRoleVerified() {
    return roleVerified;
  }

  public String getEmail() {
    return email;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public List<Project> getProjects() {
    return projects;
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

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public void setProfileDescription(String profileDescription) {
    this.profileDescription = profileDescription;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public void setAllowProjectApply(boolean allowProjectApply) {
    this.allowProjectApply = allowProjectApply;
  }

  public void setAllowProjectCreate(boolean allowProjectCreate) {
    this.allowProjectCreate = allowProjectCreate;
  }

  public void setRoleVerified(boolean roleVerified) {
    this.roleVerified = roleVerified;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void setApplicants(List<Applicant> applicants) {
    this.applicants = applicants;
  }
}