package com.Physician.Physician.exception;

public class InvalidDataException extends RuntimeException  {
    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}