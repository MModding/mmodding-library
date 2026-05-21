package com.mmodding.library.task.api;

import com.mmodding.library.task.impl.TaskRegistryImpl;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

public class TaskRegistry {

	private TaskRegistry() {}

	public static <T extends Task> void register(Identifier identifier, Codec<T> contextCodec) {
		TaskRegistryImpl.register(identifier, contextCodec);
	}
}
