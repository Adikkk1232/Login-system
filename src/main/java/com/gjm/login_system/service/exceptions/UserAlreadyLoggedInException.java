package com.gjm.login_system.service.exceptions;

public class UserAlreadyLoggedInException extends RuntimeException {
    public UserAlreadyLoggedInException(String desc) {
        super(desc);
    }
}
