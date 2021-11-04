package com.namle.socialsharing.exception;

public class BaseException extends RuntimeException {
    public BaseException() { super(); }
    public BaseException(String message) {
        super(message);
    }
    public BaseException(Throwable e) {
        super(e);
    }

    // TODO:
    //  internal exception should collect stack trace, so remove this overriding method,
    //  use the default one
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
