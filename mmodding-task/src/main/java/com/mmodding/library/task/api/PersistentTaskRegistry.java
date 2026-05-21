package com.mmodding.library.task.api;

import com.mmodding.library.task.impl.PersistentTaskRegistryImpl;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

/**
 * A class which allows defining persistence for specified tasks through the use of task context codecs.
 */
public class PersistentTaskRegistry {

	private PersistentTaskRegistry() {}

	/**
	 * Registers the task context codec under an identifier,
	 * allowing the task to be persistent through runtime executions.
	 * @param identifier the identifier
	 * @param contextCodec the task context codec
	 * @param <T> the task type
	 */
	public static <T extends Task> void register(Identifier identifier, Codec<T> contextCodec) {
		PersistentTaskRegistryImpl.register(identifier, contextCodec);
	}
}
