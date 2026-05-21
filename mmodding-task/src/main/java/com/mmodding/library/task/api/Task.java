package com.mmodding.library.task.api;

import com.mmodding.library.task.impl.InternalTaskManager;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

/**
 * A {@link Task} object, registered through {@link TaskRegistry}, can be schedule to execute some actions.
 * <br>The execution of these tasks is saved through persistent data, so that runtime interruptions cannot
 * stop these scheduled tasks.
 * <br>
 * <br>The API provides three task types:
 * <br>- {@link ExecOnceTask}, which only executes once.
 * <br>- {@link RepeatingTask}, which executes and then schedules another execution to be completed after some time.
 * <br>- {@link MultiStepTask}, which executes a step and then schedules another step to be executed after some time.
 */
@ApiStatus.NonExtendable
public interface Task {

	/**
	 * Schedules a given {@link Task} for a specified server.
	 * <br> The task class needs to be registered through {@link TaskRegistry}.
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
	 * @return the codec
	 */
	Codec<? extends Task> codec();
}
