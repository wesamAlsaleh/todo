package com.generalassembly.todo.global.exceptions;

import com.generalassembly.todo.global.dtos.ErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice // Annotation to handle exceptions globally across all controllers
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Method to handle Jakarta validation violations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationViolation(
            MethodArgumentNotValidException exception
    ) {
        // Initialize errors array
        var errors = new HashMap<String, String>();

        // Iterate through all field errors and add them to the errors map
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            // EX: ["email", "must be a well-formed email address"]
            errors.put(error.getField(), error.getDefaultMessage());
        });

        // Return bad request error with the errors as the body
        return ResponseEntity.badRequest().body(errors);
    }

    // Method to handle unique key violations "Better to use  `handleDuplicateResourceException`"
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleSQLIntegrityViolation(
            SQLIntegrityConstraintViolationException exception
    ) {
        // If the violation is `Duplicate entry` return
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }

    // Method to handle max upload size violation
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDto> handleMaxUploadSizeExceededViolation(
            MaxUploadSizeExceededException exception
    ) {
        // Return bad request error if the maximum upload size exceeded
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }

    // Method to handle Resource not found violation
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        // Return bad request error if trying to access a missing resource
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(exception.getMessage()));
    }

    // Method to handle duplication errors
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDto> handleDuplicateResourceException(DuplicateResourceException exception) {
        // Return conflict request error if trying to create a record duplicated resource
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(exception.getMessage()));
    }

    // Method to handle bad request violation
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException exception) {
        // Return bad request error if the trying to create a record with bad inputs
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }

    // Method to handle internal server errors
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorDto> handleInternalServerErrorException(InternalServerErrorException exception) {
        // Return internal server error 500 if any service failed
        return ResponseEntity.internalServerError().body(new ErrorDto(exception.getMessage()));
    }

    // Method to handle message not readable exception (bad request from the user mostly)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnReadableMessage() {
        // Return bad request error if the body is unreadable
        return ResponseEntity.badRequest().body(new ErrorDto("Unreadable Message, Please make a valid request body"));
    }

    // Method to handle duplicate key value violates "23505 postgres"
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorDto> handleDuplicateKeyValueViolation(DataIntegrityViolationException exception) {
//        // get the exception cause
//        Throwable cause = exception.getCause();
//
//
//    }


}
