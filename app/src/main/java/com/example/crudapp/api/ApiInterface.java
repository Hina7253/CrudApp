package com.example.crudapp.api;

import com.example.crudapp.models.ApiResponse;
import com.example.crudapp.models.AuthResult;
import com.example.crudapp.models.LoginRequest;
import com.example.crudapp.models.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api/Auth/student/register")
    Call<ApiResponse<AuthResult>> registerUser(@Body RegisterRequest request);

    @POST("api/Auth/student/login")
    Call<ApiResponse<AuthResult>> loginUser(@Body LoginRequest request);
}