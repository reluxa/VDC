package org.reluxa.vaadin.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GUI {
	
	Detail[] detail();
	
	Table[] table() default {};
	
}
