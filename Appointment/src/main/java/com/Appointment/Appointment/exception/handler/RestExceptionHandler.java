package com.Appointment.Appointment.exception.handler;


import com.Appointment.Appointment.dto.ErrorResponseDTO;
import com.Appointment.Appointment.exception.ConflictException;
import com.Appointment.Appointment.exception.ForbiddenException;
import com.Appointment.Appointment.exception.InvalidDataException;
import com.Appointment.Appointment.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);    }

    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseEntity<Object> handleInvalidDataException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleConflictException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


}

