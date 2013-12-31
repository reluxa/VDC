package org.reluxa.vaadin.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.vaadin.ui.Field;
import com.vaadin.ui.Table.ColumnGenerator;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	
	Class<?> context() default Object.class;

	int order() default Integer.MAX_VALUE;

	Class<? extends ColumnGenerator> type() default ColumnGenerator.class;
	
}
