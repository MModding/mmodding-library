package com.mmodding.mmodding_lib.library.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applies an environment restriction to the element used.
 * If the side is not the same as the current one it will crash.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE})
public @interface EnvRestriction {

	Side side() default Side.COMMON;

	enum Side {

		/**
		 * Means that the annotated member should be only used by the client thread.
		 */
		CLIENT_THREAD,

		/**
		 * Means that the annotated member should be only used on the client side.
		 */
		CLIENT_COMMON,

		/**
		 * Means that the annotated member can be used an all sides. This is the default value.
		 */
		COMMON,

		/**
		 * Means that the annotated member should be only used on the server side. (Integrated or Dedicated, we don't really care).
		 */
		SERVER_COMMON,

		/**
		 * Means that the annotated member should be only used by the server thread.
		 */
		SERVER_THREAD,

		/**
		 * Means that the annotated member should be only used by an integrated server thread.
		 */
		INTEGRATED_SERVER_THREAD,

		/**
		 * Means that the annotated member should be only used by a dedicated server thread.
		 */
		DEDICATED_SERVER_THREAD
	}
}
