package com.example.crudapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.crudapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(v -> registerUser());
        binding.tvLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void registerUser() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            binding.etName.setError("Name required");
            return;
        }

        if (email.isEmpty()) {
            binding.etEmail.setError("Email required");
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError("Password required");
            return;
        }

        binding.progressBar.setVisibility(android.view.View.VISIBLE);

        // Simulate registration
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(() -> {
            binding.progressBar.setVisibility(android.view.View.GONE);
            Toast.makeText(RegisterActivity.this, "Registration successful! Please login.", Toast.LENGTH_SHORT).show();
            finish();
        }, 1500);
    }
}