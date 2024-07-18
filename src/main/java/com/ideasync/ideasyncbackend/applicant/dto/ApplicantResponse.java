package com.ideasync.ideasyncbackend.applicant.dto;

import com.ideasync.ideasyncbackend.user.dto.UserResponse;

public class ApplicantResponse {
    UserResponse user;
    int status;
    public  ApplicantResponse(UserResponse user, int status) {
        this.user = user;
        this.status = status;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
