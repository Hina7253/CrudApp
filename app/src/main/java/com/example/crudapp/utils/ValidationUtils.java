package com.example.crudapp.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    private static final Pattern CONTACT_PATTERN = Pattern.compile(
            "^[0-9]{10}$"
    );

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidAge(String ageStr) {
        if (ageStr == null || ageStr.trim().isEmpty()) return false;
        try {
            int age = Integer.parseInt(ageStr);
            return age > 0 && age < 150;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidContact(String contact) {
        return contact != null && CONTACT_PATTERN.matcher(contact).matches();
    }

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}