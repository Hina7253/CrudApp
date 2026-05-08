package com.example.crudapp.models;

import com.google.gson.annotations.SerializedName;

public class AuthResult {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("role")
    private String role;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("refreshTokenExpiryTime")
    private String refreshTokenExpiryTime;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getRefreshTokenExpiryTime() { return refreshTokenExpiryTime; }
    public void setRefreshTokenExpiryTime(String refreshTokenExpiryTime) { this.refreshTokenExpiryTime = refreshTokenExpiryTime; }
}