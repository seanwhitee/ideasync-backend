package com.ideasync.ideasyncbackend.user.dto;

public class RegisterRequest {
    private String userName;
    private String password;
    private String nickName;
    private String avatarUrl;
    private String roleName;
    private String profileDescription;
    private String email;
    private String firstName;
    private String lastName;
    private boolean allowProjectApply;
    private boolean allowProjectCreate;
    private boolean roleVerified;

    public RegisterRequest(String userName, String password, String nickName, String avatarUrl, String roleName, String profileDescription, String email, String firstName, String lastName, boolean allowProjectApply, boolean allowProjectCreate, boolean roleVerified) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.roleName = roleName;
        this.profileDescription = profileDescription;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.allowProjectApply = allowProjectApply;
        this.allowProjectCreate = allowProjectCreate;
        this.roleVerified = roleVerified;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isAllowProjectApply() {
        return allowProjectApply;
    }

    public void setAllowProjectApply(boolean allowProjectApply) {
        this.allowProjectApply = allowProjectApply;
    }

    public boolean isAllowProjectCreate() {
        return allowProjectCreate;
    }

    public void setAllowProjectCreate(boolean allowProjectCreate) {
        this.allowProjectCreate = allowProjectCreate;
    }

    public boolean isRoleVerified() {
        return roleVerified;
    }

    public void setRoleVerified(boolean roleVerified) {
        this.roleVerified = roleVerified;
    }
}
