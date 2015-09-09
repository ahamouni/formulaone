package com.formulaone.dto.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CountryValidator.class)
@Target({ METHOD, FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface StateProvince {

	String message() default "{Invalid State/Province}";

	Class<?>[]groups() default {};

	Class<? extends Payload>[]payload() default {};

	String value();

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		StateProvince[]value();
	}
}
