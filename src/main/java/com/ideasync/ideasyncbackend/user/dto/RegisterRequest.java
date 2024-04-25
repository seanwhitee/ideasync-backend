package com.ideasync.ideasyncbackend.user.dto;

public class RegisterRequest {
  private String userName;
  private String password;
  private String nickName;
  private String profileDescription;
  private String avatarUrl;
  private String firstName;
  private String lastName;
  private Boolean allowProjectApply;
  private Boolean allowProjectCreate;
  private Long roleId;
  private String roleName;
  private Boolean roleVerified;

  public void setAllowProjectApply(Boolean allowProjectApply) {
    this.allowProjectApply = allowProjectApply;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public void setAllowProjectCreate(Boolean allowProjectCreate) {
    this.allowProjectCreate = allowProjectCreate;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public void setRoleVerified(Boolean roleVerified) {
    this.roleVerified = roleVerified;
  }

  public Boolean getAllowProjectApply() {
    return allowProjectApply;
  }

  public Boolean getAllowProjectCreate() {
    return allowProjectCreate;
  }

  public Long getRoleId() {
    return roleId;
  }

  public Boolean getRoleVerified() {
    return roleVerified;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getProfileDescription() {
    return profileDescription;
  }

  public void setProfileDescription(String profileDescription) {
    this.profileDescription = profileDescription;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
