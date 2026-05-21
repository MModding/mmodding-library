package com.mmodding.library.task.api;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MultiStepTask extends Task {

	/**
	 * Executes the current task at the received step.
	 * @param server the server
	 * @param step the step
	 * @return a task redirector indicating which step to execute,
	 */
	Redirector execute(MinecraftServer server, int step);

	record Redirector(int step, int delay) {

		public static final Redirector END = new Redirector(-1, 0);
	}
}
