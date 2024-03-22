package com.Appointment.Appointment.exception;

public class InvalidDataException extends RuntimeException  {
    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}