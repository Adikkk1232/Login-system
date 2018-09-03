package com.gjm.login_system.advices;

import com.gjm.login_system.controller.MainAuthenticationController;
import com.gjm.login_system.service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandlersAdvice {
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity duplicateUserExceptionHandler(Exception exc) {
        return MainAuthenticationController.createResponseEntity(HttpStatus.CONFLICT, exc.getMessage());
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity userNotLoggedInExceptionHandler(Exception exc) {
        return MainAuthenticationController.createResponseEntity(HttpStatus.UNAUTHORIZED, exc.getMessage());
    }

    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity userAlreadyLoggedInExceptionHandler(Exception exc) {
        return MainAuthenticationController.createResponseEntity(HttpStatus.UNAUTHORIZED, exc.getMessage());
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity noSuchUserExceptionHandler(Exception exc) {
        return MainAuthenticationController.createResponseEntity(HttpStatus.NOT_FOUND, exc.getMessage());
    }

    @ExceptionHandler(BadUserCredentialsException.class)
    public ResponseEntity badUserExceptionHandler(Exception exc) {
        return MainAuthenticationController.createResponseEntity(HttpStatus.FORBIDDEN, exc.getMessage());
    }
}
