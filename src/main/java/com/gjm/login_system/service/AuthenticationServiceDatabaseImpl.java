package com.gjm.login_system.service;

import com.gjm.login_system.service.exceptions.*;
import com.gjm.login_system.entity.User;
import com.gjm.login_system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * HTTP session is stored in cookie: userSession=encoded(username&password)
 * User names and emails are unique!
 */
@Service
@Qualifier("AuthenticationServiceDatabase")
public class AuthenticationServiceDatabaseImpl implements AuthenticationService {
    private UserRepository userRepository;
    private final int encodeKey;

    @Autowired
    public AuthenticationServiceDatabaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encodeKey = 27;
    }

    @Override
    public void login(LoginCredentials loginCredentials, HttpSession httpSession) {
        if(isLoggedIn(httpSession)) {
            throw new UserAlreadyLoggedInException("There is some user already logged in!");
        }

        String username = loginCredentials.getUsername();
        String password = loginCredentials.getPassword();

        if(userRepository.findUserByUsername(username) == null) {
            throw new NoSuchUserException("There isn't user with name " + username + " registered!");
        }

        String registredUserPassword = userRepository.findUserByUsername(username).getPassword();
        if(!password.equals(registredUserPassword)) {
            throw new BadUserCredentialsException("Bad password!");
        }

        setUserSession(httpSession, username + "&" + password);
    }

    @Override
    public void register(User user) {
        if(userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new DuplicateUserException("User " + user.getUsername() + " already exists!");
        }

        userRepository.save(user);
    }

    @Override
    public boolean isLoggedIn(HttpSession httpSession) {
        return httpSession.getAttribute("userSession") != null;
    }

    @Override
    public void logout(HttpSession httpSession) {
        checkIfUserIsLoggedIn(httpSession);

        httpSession.invalidate();
    }

    @Override
    public User getAuthenticatedUser(HttpSession httpSession) {
        checkIfUserIsLoggedIn(httpSession);

        String encodedSessionString = (String) httpSession.getAttribute("userSession");
        String[] sessionStringTokens = decodeSessionString(encodedSessionString).split("&");

        String username = sessionStringTokens[0];
        return userRepository.findUserByUsername(username);
    }

    private void checkIfUserIsLoggedIn(HttpSession httpSession) {
        if(!isLoggedIn(httpSession)) {
            throw new UserNotLoggedInException("User isn't logged in!");
        }
    }

    private String encodeSessionString(String sessionString) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0;i < sessionString.length();i++) {
            // We don't care about readability encoded string
            stringBuilder.append((char)(sessionString.charAt(i) + encodeKey));
        }

        return stringBuilder.toString();
    }

    private String decodeSessionString(String sessionString) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0;i < sessionString.length();i++) {
            stringBuilder.append((char)(sessionString.charAt(i) - encodeKey));
        }

        return stringBuilder.toString();
    }

    private void setUserSession(HttpSession httpSession, String userSessionDecodedString) {
        String userSessionStringEncoded = encodeSessionString(userSessionDecodedString);
        httpSession.setAttribute("userSession", userSessionStringEncoded);
    }
}
