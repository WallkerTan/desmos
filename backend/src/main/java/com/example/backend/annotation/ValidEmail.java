package com.example.backend.annotation;

import com.example.backend.validator.EmailConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailConstraintValidator.class)
public @interface ValidEmail {

    String message() default "Email không hợp lệ";

    boolean allowNull() default false;

    boolean allowEmpty() default false;

    String regex() default "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
