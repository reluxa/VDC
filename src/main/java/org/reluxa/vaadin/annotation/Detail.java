package org.reluxa.vaadin.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.vaadin.ui.Field;

@Retention(RetentionPolicy.RUNTIME)
public @interface Detail {
	
	Class<?> context() default Object.class;

	int order() default Integer.MAX_VALUE;

	Class<? extends Field> type() default Field.class;
	
}
