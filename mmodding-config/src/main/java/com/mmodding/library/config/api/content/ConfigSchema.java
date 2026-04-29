package com.mmodding.library.config.api.content;

import com.mmodding.library.config.api.Config;

import java.util.Optional;

/**
 * The configuration schema. It exists for validation and contextualization purposes.
 * <br>Unlike {@link ConfigContent}, which uses recursion, a schema stores its nodes through full property paths.
 * <br>The choice was made because it prevents from having a second recursion depth, and to make it available the same
 * instance through the whole {@link Config} object.
 */
public interface ConfigSchema {

	/**
	 * Finds a node from a property path.
	 * @param propertyPath the property path
	 * @return the optional node
	 */
	Optional<ConfigSchemaNode> findNode(String propertyPath);

	default ConfigSchemaNode findNodeOrThrow(String propertyPath) {
		return this.findNode(propertyPath).orElseThrow(() -> new IllegalArgumentException("Configuration Schema is not aware of the property path: " + propertyPath));
	}

	@SuppressWarnings("unchecked")
	default <T extends ConfigSchemaNode.Context> T findContextOrThrow(String propertyPath) {
		return (T) this.findNodeOrThrow(propertyPath).context();
	}

	default Class<?> validate(String propertyPath, Class<?> found) {
		return this.validate(propertyPath, found, this.findNodeOrThrow(propertyPath));
	}

	default Class<?> validate(String propertyPath, Class<?> found, ConfigSchemaNode node) {
		switch (node.validation()) {
			case INHERITORS -> {
				if (!node.type().isAssignableFrom(found)) {
					throw new IllegalStateException("Type mismatch on property: " + propertyPath + ", expected inheritor of: " + node.type() + " but found: " + found);
				}
			}
			case STRICT -> {
				if (!node.type().equals(found)) {
					throw new IllegalStateException("Type mismatch on property: " + propertyPath + ", expected exactly: " + node.type() + " but found: " + found);
				}
			}
		}
		return node.type();
	}
}
