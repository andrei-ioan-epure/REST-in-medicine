package com.Patient.Patient.exception;

public class InvalidDataException extends RuntimeException  {
    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}