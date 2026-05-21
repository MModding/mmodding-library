package com.mmodding.library.task.api;

import com.mmodding.library.task.impl.InternalTaskManager;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * A {@link Task} object can be scheduled to execute some actions on later time, using defined and provided context.
 * <br>
 * <br>The API provides three task types:
 * <br>- {@link ExecOnceTask}, which only executes once.
 * <br>- {@link RepeatingTask}, which executes and then schedules another execution to be completed after some time.
 * <br>- {@link MultiStepTask}, which executes a step and then schedules another step to be executed after some time.
 * <br>
 * <br>You are able to define a {@link Codec} object for the task context, and by providing it through {@link #codec()}
 * and in {@link PersistentTaskRegistry}, the execution of the task will be saved through persistent data, making it
 * independent of runtime executions.
 */
@ApiStatus.NonExtendable
public interface Task {

	/**
	 * Schedules a given {@link Task} for a specified server.
	 * <br> The task class needs to be registered through {@link PersistentTaskRegistry}.
	 * @param server the server
	 * @param task the task
	 */
	static void schedule(MinecraftServer server, Task task) {
		Task.schedule(server, task, 0);
	}

	/**
	 * Schedules a given {@link Task} for a specified server after a specified delay.
	 * @param server the server
	 * @param task the task
	 * @param after the delay (in ticks)
	 */
	static void schedule(MinecraftServer server, Task task, int after) {
		InternalTaskManager.schedule(server, task, after);
	}

	/**
	 * The codec handling the serialization for persistent data.
	 * <br>Returns null if the task is not persistent. It's the default behavior.
	 * @return the codec
	 */
	@Nullable
	default Codec<? extends Task> codec() {
		return null;
	}
}
