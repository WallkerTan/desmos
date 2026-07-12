package com.example.backend.validator;

import java.util.regex.Pattern;
import com.example.backend.annotation.ValidPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneConstrainValidator implements ConstraintValidator<ValidPhone, String> {
    private ValidPhone annotation;

    @Override
    public void initialize(ValidPhone constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null)
            return annotation.allowNull();
        if (phone.trim().isEmpty())
            return annotation.allowEmpty();
        Pattern pattent = Pattern.compile(annotation.regex());
        return pattent.matcher(phone).matches();
    }

}
