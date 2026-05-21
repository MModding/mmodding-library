package com.mmodding.library.task.api;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

public interface RepeatingTask extends Task {

	/**
	 * Executes the repeating task, and tells how many ticks to wait before executing then next one.
	 * @param server the server
	 * @return the ticks amount before next execution
	 */
	@ApiStatus.OverrideOnly
	int execute(MinecraftServer server);
}
