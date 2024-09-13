package com.ideasync.ideasyncbackend.user.dto;

import java.util.UUID;

public class UpdateRequest {
    private UUID id;
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

    // Getters and setters

    public UUID getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
