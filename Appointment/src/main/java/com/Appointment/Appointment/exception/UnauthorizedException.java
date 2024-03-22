package com.Appointment.Appointment.exception;

public class UnauthorizedException extends RuntimeException  {
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}