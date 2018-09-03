package com.gjm.login_system.service;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginCredentials {
    @NotEmpty(message = "Username field can't be empty")
    private String username;

    @NotEmpty(message = "Password field can't be empty")
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
