package com.gjm.login_system.service.exceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String desc) {
        super(desc);
    }
}
