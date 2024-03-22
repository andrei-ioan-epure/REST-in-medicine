package com.Patient.Patient.exception;

public class UnauthorizedException extends RuntimeException  {
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}