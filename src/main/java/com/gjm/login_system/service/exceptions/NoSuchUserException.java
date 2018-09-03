package com.gjm.login_system.service.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String desc) {
        super(desc);
    }
}
