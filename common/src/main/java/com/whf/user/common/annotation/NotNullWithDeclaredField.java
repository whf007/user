package com.whf.user.common.annotation;

import com.whf.user.common.validate.NotNullWithDeclaredFieldConstraint;
import jodd.vtor.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(NotNullWithDeclaredFieldConstraint.class)
public @interface NotNullWithDeclaredField {

    String value();

    String[] profiles() default {};

    int severity() default 0;

    String message() default "两个字段不可同时为空";
}
