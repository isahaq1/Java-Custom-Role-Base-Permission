package com.dxerp.ebs.util;

public class AuthResponse {
    private String token;
    private Object data; // User details or additional data
    private String status;
    private String message;

    public AuthResponse(String token, Object data, String status,String message) {
        this.token = token;
        this.data = data;
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}