package com.claudionetto.gamelibrary.exceptions;

import java.time.LocalDateTime;

public class ValidationExceptionResponse extends ExceptionResponse{
    private final String fields;
    private final String fieldsMessage;

    public ValidationExceptionResponse(String fields, String fieldsMessage) {
        this.fields = fields;
        this.fieldsMessage = fieldsMessage;
    }

    public ValidationExceptionResponse(String title, String details, int status, LocalDateTime timeStamp, String path, String fields, String fieldsMessage) {
        super(title, details, status, timeStamp, path);
        this.fields = fields;
        this.fieldsMessage = fieldsMessage;
    }

    public String getFields() {
        return fields;
    }

    public String getFieldsMessage() {
        return fieldsMessage;
    }

    public static final class ValidationExceptionResponseBuilder {
        private String title;
        private String details;
        private int status;
        private LocalDateTime timeStamp;
        private String path;
        private String fields;
        private String fieldsMessage;

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

        public ValidationExceptionResponseBuilder fields(String fields) {
            this.fields = fields;
            return this;
        }

        public ValidationExceptionResponseBuilder fieldsMessage(String fieldsMessage) {
            this.fieldsMessage = fieldsMessage;
            return this;
        }

        public ValidationExceptionResponse build() {
            ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse(fields, fieldsMessage);
            validationExceptionResponse.setTitle(title);
            validationExceptionResponse.setDetails(details);
            validationExceptionResponse.setStatus(status);
            validationExceptionResponse.setTimeStamp(timeStamp);
            validationExceptionResponse.setPath(path);
            return validationExceptionResponse;
        }
    }
}
