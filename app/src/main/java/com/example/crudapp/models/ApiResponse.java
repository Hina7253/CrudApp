package com.example.crudapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse<T> {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private List<String> errors;

    @SerializedName("result")
    private T result;

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }

    public T getResult() { return result; }
    public void setResult(T result) { this.result = result; }
}