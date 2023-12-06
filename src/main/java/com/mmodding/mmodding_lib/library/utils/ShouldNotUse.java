package com.mmodding.mmodding_lib.library.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the specific element shouldn't be used for classic purposes.
 * It can be used for implementations if the target is an unimplemented method.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ShouldNotUse {

	/**
	 * Targets the name of the method that should be used instead.
	 * You should use the method descriptor instead when necessary.
	 */
	String useInstead() default "nothing";
}
