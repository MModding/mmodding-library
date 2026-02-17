package com.mmodding.library.config.api.schema;

import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.config.impl.schema.ConfigSchemaImpl;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

/**
 * The Configuration Schema. It explicits the specification of the configuration to follow, limiting its content to a set of rules.
 */
@ApiStatus.NonExtendable
public interface ConfigSchema {

	/**
	 * Creates a new {@link ConfigSchema}, ready to be specified.
	 * @return the newly created config schema
	 */
	static ConfigSchema create() {
		return new ConfigSchemaImpl();
	}

	/**
	 * Here be dragons!
	 * <br><br>
	 * The <code>EMPTY</code> configuration schema means that your configuration will not be using a schema
	 * to rule its content.
	 * <br><br>
	 * This implies that every value stored inside a configuration file will be processed as-is, and that you will not be able to make use of custom elements!
	 */
	static ConfigSchema empty() {
		return new ConfigSchemaImpl.EmptySchema();
	}

	/**
	 * Specifies a boolean value at this location.
	 * @param qualifier the qualifier
	 * @return the current schema
	 */
	ConfigSchema bool(String qualifier);

	/**
	 * Specifies an integer value at this location.
	 * @param qualifier the qualifier
	 * @return the current schema
	 */
	ConfigSchema integer(String qualifier);

	/**
	 * Specifies a double floating point value at this location.
	 * @param qualifier the qualifier
	 * @return the current schema
	 */
	ConfigSchema floating(String qualifier);

	/**
	 * Specifies a string value at this location.
	 * @param qualifier the qualifier
	 * @return the current schema
	 */
	ConfigSchema string(String qualifier);

	/**
	 * Specifies a color value at this location.
	 * @param qualifier the qualifier
	 * @return the current schema
	 */
	ConfigSchema color(String qualifier);

	/**
	 * Specifies an integer range at this location.
	 * @param qualifier the qualifier
	 * @param start the first boundary of the range
	 * @param end the second boundary of the range
	 * @return the current schema
	 */
	ConfigSchema integerRange(String qualifier, int start, int end);

	/**
	 * Specifies a double floating point range at this location.
	 * @param qualifier the qualifier
	 * @param start the first boundary of the range
	 * @param end the second boundary of the range
	 * @return the current schema
	 */
	ConfigSchema floatingRange(String qualifier, double start, double end);

	/**
	 * Specifies an enumeration value at this location. The enumeration names should be using <code>UPPER_SNAKE_CASE</code> in Java code and <code>snake_case</code> in JSON objects.
	 * @param qualifier the qualifier
	 * @param enumClass the enumeration class
	 * @return the current schema
	 * @param <T> the enumeration class type
	 */
	<T extends Enum<T>> ConfigSchema enumValue(String qualifier, Class<T> enumClass);

	/**
	 * Specifies a list at this location.
	 * @param qualifier the qualifier
	 * @return the current schema
	 */
	ConfigSchema list(String qualifier);

	/**
	 * Specifies a new category at this location, and specifies its inner content.
	 * @param qualifier the qualifier
	 * @param category the category's content specification
	 * @return the current schema
	 */
	ConfigSchema category(String qualifier, Consumer<ConfigSchema> category);

	/**
	 * Specifies a new custom value at this location, for a registered {@link ConfigElementTypeWrapper}.
	 * @param qualifier the qualifier
	 * @param type the wrapped element type
	 * @return the current schema
	 * @param <T> the wrapped element class type
	 */
	<T> ConfigSchema custom(String qualifier, Class<T> type);

	/**
	 * Specifies a new custom value at this location, for a registered {@link ConfigElementTypeWrapper}, along with its properties.
	 * @param qualifier the qualifier
	 * @param type the wrapped element type
	 * @param properties the properties of this element
	 * @return the current schema
	 * @param <T> the wrapper element class type
	 * @param <P> the wrapped element properties class type
	 */
	<T, P extends ConfigElementTypeWrapper.Properties> ConfigSchema custom(String qualifier, Class<T> type, P properties);
}
