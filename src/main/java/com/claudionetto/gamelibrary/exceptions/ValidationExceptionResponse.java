package com.claudionetto.gamelibrary.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionResponse extends ExceptionResponse{

    private final Map<String, String> fieldErrorMap;

    public ValidationExceptionResponse(Map<String, String> fieldErrorMap) {
        this.fieldErrorMap = fieldErrorMap;
    }

    public ValidationExceptionResponse(String title, String details, int status, LocalDateTime timeStamp, String path, Map<String, String> fieldErrorMap) {
        super(title, details, status, timeStamp, path);
        this.fieldErrorMap = fieldErrorMap;
    }

    public Map<String, String> getFieldErrorMap() {
        return fieldErrorMap;
    }

    public static final class ValidationExceptionResponseBuilder {
        private String title;
        private String details;
        private int status;
        private LocalDateTime timeStamp;
        private String path;
        private Map<String, String> fieldErrorMap;

        private ValidationExceptionResponseBuilder() {
        }

        public static ValidationExceptionResponseBuilder aValidationExceptionResponse() {
            return new ValidationExceptionResponseBuilder();
        }

        public ValidationExceptionResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ValidationExceptionResponseBuilder details(String details) {
            this.details = details;
            return this;
        }

        public ValidationExceptionResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ValidationExceptionResponseBuilder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public ValidationExceptionResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ValidationExceptionResponseBuilder fieldErrorMap(Map<String, String> fieldErrorMap) {
            this.fieldErrorMap = fieldErrorMap;
            return this;
        }

        public ValidationExceptionResponse build() {
            ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse(fieldErrorMap);
            validationExceptionResponse.setTitle(title);
            validationExceptionResponse.setDetails(details);
            validationExceptionResponse.setStatus(status);
            validationExceptionResponse.setTimeStamp(timeStamp);
            validationExceptionResponse.setPath(path);
            return validationExceptionResponse;
        }
    }
}
