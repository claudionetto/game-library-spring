package com.claudionetto.gamelibrary.handlers;

import com.claudionetto.gamelibrary.exceptions.UserAlreadyExistsException;
import com.claudionetto.gamelibrary.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("errors/user-not-found"));
        problemDetail.setTitle("Not found exception, check the documentation");
        problemDetail.setProperty("timeStamp", LocalDateTime.now());

        return problemDetail;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleNotFoundException(
            UserAlreadyExistsException ex, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setType(URI.create("errors/user-already-exists"));
        problemDetail.setTitle("User already exists exception, check the documentation");
        problemDetail.setProperty("timeStamp", LocalDateTime.now());

        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> fieldErrorsMap = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Field error"));
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Check the field(s) error(s): " + fields );

        problemDetail.setType(URI.create("errors/bad-request-exception"));
        problemDetail.setTitle("Bad Request Exception, invalid fields");
        problemDetail.setProperty("timeStamp", LocalDateTime.now());
        problemDetail.setProperty("errors", fieldErrorsMap);

        return problemDetail;
    }

}


