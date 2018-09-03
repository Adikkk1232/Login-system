package com.gjm.login_system.controller;

import com.gjm.login_system.entity.User;
import com.gjm.login_system.service.AuthenticationService;
import com.gjm.login_system.service.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class MainAuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public MainAuthenticationController(@Qualifier("AuthenticationServiceDatabase") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@Valid User user, Errors errors, HttpSession httpSession) {
        if(errors.hasErrors()) {
            return createResponseEntity(HttpStatus.BAD_REQUEST, stringifyValidationErrors(errors));
        }

        if(authenticationService.isLoggedIn(httpSession)) {
            return createResponseEntity(HttpStatus.UNAUTHORIZED, "User is already logged in!");
        }

        authenticationService.register(user);

        return createResponseEntity(HttpStatus.OK, user + " successfully registered!");
    }

    @RequestMapping(value = "/getAuthenticatedUser", method = RequestMethod.POST)
    public ResponseEntity getAuthenticatedUser(HttpSession httpSession) {
        return createResponseEntity(HttpStatus.OK, authenticationService.getAuthenticatedUser(httpSession));
    }

    @RequestMapping(value = "/isLoggedIn", method = RequestMethod.GET)
    public ResponseEntity isUserLoggedIn(HttpSession httpSession) {
        if(authenticationService.isLoggedIn(httpSession)) {
            return createResponseEntity(HttpStatus.OK, "");
        } else {
            return createResponseEntity(HttpStatus.NOT_FOUND, "");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@Valid LoginCredentials loginCredentials, Errors errors, HttpSession httpSession) {
        if(errors.hasErrors()) {
            return createResponseEntity(HttpStatus.BAD_REQUEST, stringifyValidationErrors(errors));
        }

        authenticationService.login(loginCredentials, httpSession);

        return createResponseEntity(HttpStatus.OK, "");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {
        authenticationService.logout(httpSession);

        return createResponseEntity(HttpStatus.OK, "");
    }

    public static ResponseEntity createResponseEntity(HttpStatus httpStatus, Object responseBody) {
        return ResponseEntity
                .status(httpStatus)
                .body(responseBody);
    }

    private String stringifyValidationErrors(Errors errors) {
        StringBuilder stringifiedValidationErrors = new StringBuilder();

        for(ObjectError error : errors.getAllErrors()) {
            stringifiedValidationErrors.append(error.getDefaultMessage());
            stringifiedValidationErrors.append("\n");
        }

        return stringifiedValidationErrors.toString();
    }
}
