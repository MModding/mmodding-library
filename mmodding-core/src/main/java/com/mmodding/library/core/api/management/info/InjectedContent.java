package com.mmodding.library.core.api.management.info;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the current interface is being implemented to other classes by interface injection.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface InjectedContent {

	/**
	 * @return targeted classes by the injection
	 */
	Class<?>[] value();
}
