package com.Physician.Physician.exception;

public class ConflictException extends RuntimeException  {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}