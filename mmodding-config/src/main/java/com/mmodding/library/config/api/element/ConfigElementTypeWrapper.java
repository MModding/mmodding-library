package com.mmodding.library.config.api.element;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;

/**
 * An interface which enables representation of custom elements inside the configuration
 * @param <T> wrapper type
 * @param <V> value type - can be the same as <code>T</code>, it usually differs when the wrapper also wraps properties
 * @param <P> properties (immutable, defined by the schema)
 */
public interface ConfigElementTypeWrapper<T, V, P extends ConfigElementTypeWrapper.Properties> {

	/**
	 * The resolver for the config element type.
	 * @param category the current category
	 * @param qualifier the qualifier of the element to resolve
	 * @param properties the properties given by the schema
	 * @return the element wrapped to its type
	 */
	T resolve(ConfigContent category, String qualifier, P properties);

	/**
	 * The modifier for the config element type.
	 * @param mutable the current category
	 * @param qualifier the qualifier of the element to modify
	 * @param properties the properties given by the schema
	 * @param value the modification value
	 */
	void modify(MutableConfigContent mutable, String qualifier, P properties, V value);

	/**
	 * A parent for property implementations, which can be used as-is if the wrapper do not deal with properties
	 * (for cases like this, all values of this type should be null).
	 */
	interface Properties {}
}
