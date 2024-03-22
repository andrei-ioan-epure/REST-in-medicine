package com.consultations.Consultations.exception.handler;

import com.consultations.Consultations.dto.ErrorResponseDTO;
import com.consultations.Consultations.exception.ForbiddenException;
import com.consultations.Consultations.exception.InvalidDataException;
import com.consultations.Consultations.exception.UnauthorizedException;
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
}

