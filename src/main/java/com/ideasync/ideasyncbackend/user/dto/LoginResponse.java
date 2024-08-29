package com.ideasync.ideasyncbackend.user.dto;

import com.ideasync.ideasyncbackend.authorization.dto.Token;

public class LoginResponse {
    private UserResponse userResponse;
    private Token token;

    public LoginResponse(Token token, UserResponse userResponse) {
        this.token = token;
        this.userResponse = userResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
