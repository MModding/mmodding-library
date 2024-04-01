package com.mmodding.library.core.impl.registry;

import com.mmodding.library.core.api.Reference;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ReferenceLiteFactoryImpl implements Reference.LiteFactory {

	private final Function<String, ? extends Identifier> factory;

	public ReferenceLiteFactoryImpl(Function<String, ? extends Identifier> factory) {
		this.factory = factory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Reference<T> createId(String path) {
		return (Reference<T>) this.factory.apply(path);
	}
}
