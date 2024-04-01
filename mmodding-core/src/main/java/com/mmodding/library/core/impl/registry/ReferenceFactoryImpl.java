package com.mmodding.library.core.impl.registry;

import com.mmodding.library.core.api.Reference;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public class ReferenceFactoryImpl implements Reference.Factory {

	private final BiFunction<String, String, ? extends Identifier> factory;

	public ReferenceFactoryImpl(BiFunction<String, String, ? extends Identifier> factory) {
		this.factory = factory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Reference<T> createId(String namespace, String path) {
		return (Reference<T>) this.factory.apply(namespace, path);
	}
}
