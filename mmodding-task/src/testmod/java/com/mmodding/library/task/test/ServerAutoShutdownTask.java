package com.mmodding.library.task.test;

import com.mmodding.library.task.api.ExecOnceTask;
import net.minecraft.server.MinecraftServer;

public record ServerAutoShutdownTask(String message) implements ExecOnceTask {

	@Override
	public void execute(MinecraftServer server) {
		System.out.println(this.message);
		server.close();
	}
}
