package com.okta.developer.jugtours.model.response;

public class ApiResponse<T> {
    private int status;          // HTTP status code
    private String message;      // Custom message
    private T data;              // Data payload
    private Object errors;       // Validation or other errors

    // Constructors
    public ApiResponse() {}

    public ApiResponse(int status, String message, T data, Object errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    // Factory methods for success and error responses
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data, null);
    }

    public static <T> ApiResponse<T> error(String message, Object errors) {
        return new ApiResponse<>(400, message, null, errors);
    }
}
