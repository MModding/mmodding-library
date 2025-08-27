package com.mmodding.library.core.api.management.content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the current interface is a duck interface.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DuckInterface {

	/**
	 * @return targeted classes by the duck interface
	 */
	Class<?>[] value();
}
