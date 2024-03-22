package com.Physician.Physician.exception;

public class UnauthorizedException extends RuntimeException  {
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}