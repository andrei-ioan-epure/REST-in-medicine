package com.Patient.Patient.exception;

public class ConflictException extends RuntimeException  {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}