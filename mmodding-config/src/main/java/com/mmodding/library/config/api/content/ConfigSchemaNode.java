package com.mmodding.library.config.api.content;

import org.jetbrains.annotations.Nullable;

/**
 * A node of a configuration schema. It contains validation information and contextualization of the current location.
 * @param type the element type
 * @param validation the validation
 * @param context the context
 */
public record ConfigSchemaNode(Class<?> type, Validation validation, @Nullable Context context) {

	public ConfigSchemaNode(Class<?> type, Validation validation, @Nullable Context context) {
		this.type = type;
		this.validation = validation;
		if (context != null && !context.matches(type)) {
			throw new IllegalArgumentException("Context " + context.getClass() + " does not match with the provided type " + type);
		}
		else {
			this.context = context;
		}
	}

	public static ConfigSchemaNode of(Class<?> type) {
		return ConfigSchemaNode.of(type, Validation.INHERITORS);
	}

	public static ConfigSchemaNode of(Class<?> type, Validation validation) {
		return new ConfigSchemaNode(type, validation, null);
	}

	public static ConfigSchemaNode of(Class<?> type, Context context) {
		return new ConfigSchemaNode(type, Validation.INHERITORS, context);
	}

	/**
	 * There are two validation modes.
	 * <br><code>INHERITORS</code> allows elements which's types are inheriting from the node's element type.
	 * <br><code>STRICT</code> forces every provided element to be of the exact same type.
	 */
	public enum Validation {
		INHERITORS,
		STRICT
	}

	/**
	 * Represents containing multiple unmodifiable properties.
	 * <br>For example: integer ranges and double ranges uses this to store their boundaries.
	 * <br>It can then be used by the gui.
	 */
	public interface Context {

		boolean matches(Class<?> type);
	}
}
