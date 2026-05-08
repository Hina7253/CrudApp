package com.example.crudapp.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("name")
    private String name;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("password")
    private String password;

    public RegisterRequest(String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}