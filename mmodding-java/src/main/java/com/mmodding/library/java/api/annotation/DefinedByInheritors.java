package com.mmodding.library.java.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated constructor parameter should be set by method overloading inside of inheritors.
 */
@Target(ElementType.PARAMETER)
public @interface DefinedByInheritors {
}
