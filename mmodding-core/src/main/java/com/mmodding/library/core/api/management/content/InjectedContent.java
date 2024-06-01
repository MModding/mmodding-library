package com.mmodding.library.core.api.management.content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the current class is being injected
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface InjectedContent {

	/**
	 * @return targeted classes by the injection
	 */
	Class<?>[] value();
}
