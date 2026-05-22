package com.mmodding.library.task.impl;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.task.api.Task;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

import java.util.Objects;

public class PersistentTaskRegistryImpl {

	public static final LiteRegistry<Codec<? extends Task>> CODECS = LiteRegistry.create();

	private PersistentTaskRegistryImpl() {}

	public static <T extends Task> void register(Identifier identifier, Codec<T> contextCodec) {
		CODECS.register(identifier, contextCodec);
	}

	public static Codec<? extends Task> get(Identifier identifier) {
		return Objects.requireNonNull(CODECS.get(identifier), "Unregistered Persistent Task Context Codec for " + identifier);
	}

	public static Identifier getCodecId(Task task) {
		return Objects.requireNonNull(CODECS.getId(task.codec()), "Unregistered Persistent Task Context Codec for class " + task.getClass());
	}
}
