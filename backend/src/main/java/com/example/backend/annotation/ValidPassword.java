package com.example.backend.annotation;

import com.example.backend.validator.PasswordConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface ValidPassword {

    String message() default "Password không hợp lệ";

    boolean allowNull() default false;

    boolean allowEmpty() default false;

    // Ít nhất 8 ký tự, có chữ thường, chữ hoa, số và ký tự đặc biệt
    String regex() default "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*._]).{8,}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
