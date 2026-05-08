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
import com.example.crudapp.models.LoginRequest;
import com.example.crudapp.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhoneNumber, etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private TextView tvError, tvRegisterLink;
    private SessionManager sessionManager;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

        // Already logged in check
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        initViews();

        btnLogin.setOnClickListener(v -> performLogin());

        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
    }

    private void performLogin() {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.setError("Phone number required");
            return;
        }
        if (phoneNumber.length() < 10) {
            etPhoneNumber.setError("Valid 10-digit phone number required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            return;
        }

        showLoading(true);
        tvError.setVisibility(android.view.View.GONE);

        LoginRequest request = new LoginRequest(phoneNumber, password);
        Call<ApiResponse<AuthResult>> call = apiInterface.loginUser(request);

        call.enqueue(new Callback<ApiResponse<AuthResult>>() {
            @Override
            public void onResponse(Call<ApiResponse<AuthResult>> call, Response<ApiResponse<AuthResult>> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<AuthResult> apiResponse = response.body();

                    if (apiResponse.isSuccess() && apiResponse.getResult() != null) {
                        AuthResult result = apiResponse.getResult();

                        sessionManager.createLoginSession(
                                result.getId(),
                                result.getName(),
                                result.getPhoneNumber(),
                                result.getAccessToken(),
                                result.getRefreshToken()
                        );

                        Toast.makeText(LoginActivity.this,
                                apiResponse.getMessage() != null ? apiResponse.getMessage() : "Login Successful!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = apiResponse.getMessage();
                        if (apiResponse.getErrors() != null && !apiResponse.getErrors().isEmpty()) {
                            errorMsg = apiResponse.getErrors().get(0);
                        }
                        tvError.setText("❌ " + (errorMsg != null ? errorMsg : "Login failed"));
                        tvError.setVisibility(android.view.View.VISIBLE);
                    }
                } else {
                    tvError.setText("❌ Login failed. Please check your credentials.");
                    tvError.setVisibility(android.view.View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthResult>> call, Throwable t) {
                showLoading(false);
                tvError.setText("❌ Network Error: " + t.getMessage());
                tvError.setVisibility(android.view.View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? android.view.View.VISIBLE : android.view.View.GONE);
        btnLogin.setEnabled(!show);
        btnLogin.setText(show ? "Logging in..." : "Login");
    }
}