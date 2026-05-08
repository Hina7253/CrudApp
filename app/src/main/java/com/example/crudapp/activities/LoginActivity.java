package com.example.crudapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.crudapp.databinding.ActivityLoginBinding;
import com.example.crudapp.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        // Check if already logged in
        if (sessionManager.isLoggedIn()) {
            navigateToMain();
            return;
        }

        binding.btnLogin.setOnClickListener(v -> loginUser());
        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            binding.etEmail.setError("Email required");
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError("Password required");
            return;
        }

        binding.progressBar.setVisibility(android.view.View.VISIBLE);

        // Simple validation (in real app, call your API)
        if (email.equals("test@test.com") && password.equals("123456")) {
            sessionManager.createLoginSession("Test User");
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            navigateToMain();
        } else {
            binding.progressBar.setVisibility(android.view.View.GONE);
            Toast.makeText(this, "Invalid credentials!\nUse test@test.com / 123456", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}