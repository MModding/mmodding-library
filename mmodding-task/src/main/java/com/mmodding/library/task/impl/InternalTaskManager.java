package com.mmodding.library.task.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.task.api.Task;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashSet;
import java.util.Set;

public class InternalTaskManager extends SavedData {

	public static final SavedDataType<InternalTaskManager> TYPE = new SavedDataType<>(
		MModdingLibrary.createId("tasks"),
		InternalTaskManager::new,
		Codec.list(TaskHolder.CODEC)
			.xmap(
				holders -> {
					InternalTaskManager result = new InternalTaskManager();
					result.holders.addAll(holders);
					return result;
				},
				manager -> manager.holders.stream().filter(TaskHolder::isPersistent).toList()
			),
		null
	);

	private final Set<TaskHolder> holders = new HashSet<>();

	public static void schedule(MinecraftServer server, Task task, int after) {
		TaskHolder holder = new TaskHolder(task, after);
		if (after != 0 || !holder.tick(server)) {
			((Duck) server).mmodding_library$manager().holders.add(holder);
		}
	}

	public void tick(MinecraftServer server) {
		this.holders.removeIf(holder -> holder.tick(server));
	}

	@Override
	public boolean isDirty() {
		return !this.holders.isEmpty();
	}

	public interface Duck {

		InternalTaskManager mmodding_library$manager();
	}
}
