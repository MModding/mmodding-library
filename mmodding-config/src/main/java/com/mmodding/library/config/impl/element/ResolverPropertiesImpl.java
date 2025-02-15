package com.mmodding.library.config.impl.element;

import com.mmodding.library.config.api.element.type.ConfigElementTypeResolver;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public record ResolverPropertiesImpl(Map<String, ?> map) implements ConfigElementTypeResolver.ResolverProperties {

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String property) {
		return (T) this.map.get(property);
	}
}
