package com.mmodding.library.task.api;

import net.minecraft.server.MinecraftServer;

public interface ExecOnceTask extends Task {

	/**
	 * Executes the task once.
	 * @param server the server
	 */
	void execute(MinecraftServer server);
}
