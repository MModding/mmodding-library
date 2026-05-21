package com.mmodding.library.task.api;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

public interface ExecOnceTask extends Task {

	/**
	 * Executes the task once.
	 * @param server the server
	 */
	@ApiStatus.OverrideOnly
	void execute(MinecraftServer server);
}
