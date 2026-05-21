package com.mmodding.library.task.impl;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.task.api.Task;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

public class TaskRegistryImpl {

	public static final LiteRegistry<Codec<? extends Task>> CODECS = LiteRegistry.create();

	private TaskRegistryImpl() {}

	public static <T extends Task> void register(Identifier identifier, Codec<T> contextCodec) {
		CODECS.register(identifier, contextCodec);
	}
}
