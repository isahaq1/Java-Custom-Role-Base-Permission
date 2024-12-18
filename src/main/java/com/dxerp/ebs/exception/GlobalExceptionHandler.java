package com.dxerp.ebs.exception;

import com.dxerp.ebs.security.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        // Return a 403 Forbidden response with the exception message
        // return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(), 
                HttpStatus.FORBIDDEN.value()  // You can use HttpStatus.FORBIDDEN.value() for the status code
        );

        // Return the custom error response with a 403 Forbidden status
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

   

    public class ErrorResponse {
        private String message;
        private int status;
    
        // Constructor
        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    
        // Getters and setters
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    
        public int getStatus() {
            return status;
        }
    
        public void setStatus(int status) {
            this.status = status;
        }
    }
}