package com.mmodding.library.config.api.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Specifies that the targeted class represents a configuration element
 */
@Target(ElementType.TYPE)
public @interface ConfigElement {
}
