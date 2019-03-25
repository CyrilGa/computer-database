package fr.cgaiton611.cdb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateAndTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAndTimeAnnotation {
	String message() default "{date and time must be null or not null together}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
