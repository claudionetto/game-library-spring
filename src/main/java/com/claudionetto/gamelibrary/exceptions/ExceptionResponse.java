package com.claudionetto.gamelibrary.exceptions;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private String title;
    private String details;
    private int status;
    private LocalDateTime timeStamp;
    private String path;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String title, String details, int status, LocalDateTime timeStamp, String path) {
        this.title = title;
        this.details = details;
        this.status = status;
        this.timeStamp = timeStamp;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public static final class ExceptionResponseBuilder {
        private String title;
        private String details;
        private int status;
        private LocalDateTime timeStamp;
        private String path;

        private ExceptionResponseBuilder() {
        }

        public static ExceptionResponseBuilder anExceptionResponse() {
            return new ExceptionResponseBuilder();
        }

        public ExceptionResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ExceptionResponseBuilder details(String details) {
            this.details = details;
            return this;
        }

        public ExceptionResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ExceptionResponseBuilder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public ExceptionResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ExceptionResponse build() {
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            exceptionResponse.setTitle(title);
            exceptionResponse.setDetails(details);
            exceptionResponse.setStatus(status);
            exceptionResponse.setTimeStamp(timeStamp);
            exceptionResponse.setPath(path);
            return exceptionResponse;
        }
    }
}
