package com.example.crudapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.crudapp.R;
import com.example.crudapp.api.RetrofitClient;
import com.example.crudapp.api.ApiInterface;
import com.example.crudapp.models.ApiResponse;
import com.example.crudapp.models.AuthResult;
import com.example.crudapp.models.RegisterRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etPhoneNumber, etPassword, etConfirmPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    private TextView tvError, tvLoginLink;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        initViews();

        btnRegister.setOnClickListener(v -> performRegister());

        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        tvLoginLink = findViewById(R.id.tvLoginLink);
    }

    private void performRegister() {
        String name = etFullName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etFullName.setError("Full name required");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 10) {
            etPhoneNumber.setError("Valid 10-digit mobile number required");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        showLoading(true);
        tvError.setVisibility(android.view.View.GONE);

        RegisterRequest request = new RegisterRequest(name, phoneNumber, password);
        Call<ApiResponse<AuthResult>> call = apiInterface.registerUser(request);

        call.enqueue(new Callback<ApiResponse<AuthResult>>() {
            @Override
            public void onResponse(Call<ApiResponse<AuthResult>> call, Response<ApiResponse<AuthResult>> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<AuthResult> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        Toast.makeText(RegisterActivity.this,
                                apiResponse.getMessage() != null ? apiResponse.getMessage() : "Registration Successful!",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = apiResponse.getMessage();
                        if (apiResponse.getErrors() != null && !apiResponse.getErrors().isEmpty()) {
                            errorMsg = apiResponse.getErrors().get(0);
                        }
                        tvError.setText("❌ " + errorMsg);
                        tvError.setVisibility(android.view.View.VISIBLE);
                    }
                } else {
                    tvError.setText("❌ Registration failed. Please try again.");
                    tvError.setVisibility(android.view.View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthResult>> call, Throwable t) {
                showLoading(false);
                tvError.setText("❌ Network Error: " + t.getMessage());
                tvError.setVisibility(android.view.View.VISIBLE);
                Toast.makeText(RegisterActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? android.view.View.VISIBLE : android.view.View.GONE);
        btnRegister.setEnabled(!show);
        btnRegister.setText(show ? "Registering..." : "Register");
    }
}