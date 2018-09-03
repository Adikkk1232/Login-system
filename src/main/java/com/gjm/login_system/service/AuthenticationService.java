package com.gjm.login_system.service;

import com.gjm.login_system.entity.User;

import javax.servlet.http.HttpSession;

public interface AuthenticationService {
    void login(LoginCredentials loginCredentials, HttpSession httpSession);
    void register(User user);

    boolean isLoggedIn(HttpSession httpSession);
    void logout(HttpSession httpSession);

    User getAuthenticatedUser(HttpSession httpSession);
}
