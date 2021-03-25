package com.cpiinfo.authnz.exception;

import org.springframework.security.core.AuthenticationException;

public class UserLockException  extends AuthenticationException {
    public UserLockException(String msg) {
        super(msg);
    }

    public UserLockException(String msg, Throwable t) {
        super(msg, t);
    }

}
