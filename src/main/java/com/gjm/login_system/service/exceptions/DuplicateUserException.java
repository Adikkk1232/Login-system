package com.gjm.login_system.service.exceptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String desc) {
        super(desc);
    }
}
