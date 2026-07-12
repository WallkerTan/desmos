package com.example.backend.annotation;

import com.example.backend.validator.PhoneConstrainValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneConstrainValidator.class)
public @interface ValidPhone {

    String message() default "Số điện thoại không hợp lệ";

    boolean allowNull() default false;

    boolean allowEmpty() default false;

    // Đầu số Việt Nam
    String regex() default "^(0[35789])\\d{8}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
