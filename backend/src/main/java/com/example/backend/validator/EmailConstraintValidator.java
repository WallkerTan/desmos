package com.example.backend.validator;

import java.util.regex.Pattern;
import com.example.backend.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {

    private ValidEmail annotation;


    // hàm khỏi tạo của ConstraintValidator
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Logic validate email
        if (email == null) {
            return annotation.allowNull();
        }

        if (email.trim().isEmpty()) {
            return annotation.allowEmpty();
        }
        Pattern pattern = Pattern.compile(annotation.regex());
        return pattern.matcher(email).matches();
    }
}
