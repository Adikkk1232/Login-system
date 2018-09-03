package com.gjm.login_system.service.exceptions;

public class BadUserCredentialsException extends RuntimeException {
    public BadUserCredentialsException(String desc) {
        super(desc);
    }
}
