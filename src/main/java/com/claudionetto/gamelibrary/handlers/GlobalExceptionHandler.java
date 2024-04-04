package com.claudionetto.gamelibrary.handlers;

import com.claudionetto.gamelibrary.exceptions.ExceptionResponse;
import com.claudionetto.gamelibrary.exceptions.NotFoundException;
import com.claudionetto.gamelibrary.exceptions.ValidationExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(
            NotFoundException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                ExceptionResponse.ExceptionResponseBuilder.anExceptionResponse()
                        .title("Not found exception, check the documentation")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(ex.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionResponse.ValidationExceptionResponseBuilder.aValidationExceptionResponse()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, invalid fields")
                        .details("Check the field(s) error(s)" + fields)
                        .path(request.getRequestURI())
                        .fields(fields)
                        .fieldsMessage(fieldMessages)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}


