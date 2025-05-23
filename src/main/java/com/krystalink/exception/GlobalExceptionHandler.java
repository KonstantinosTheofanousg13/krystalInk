package com.krystalink.exception;

import com.krystalink.helper.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getPropertyPath() + ": " + violation.getMessage()));

        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateClientException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateClientException(DuplicateClientException ex) {
        List<String> errors = List.of(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Duplicate client", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException ex) {
        List<String> errors = List.of(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Client not found", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateAppointmentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAppointmentException(DuplicateAppointmentException ex) {
        List<String> errors = List.of(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Duplicate appointment", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
