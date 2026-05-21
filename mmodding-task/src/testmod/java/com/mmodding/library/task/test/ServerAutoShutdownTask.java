package com.mmodding.library.task.test;

import com.mmodding.library.task.api.ExecOnceTask;
import com.mmodding.library.task.api.Task;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;

public record ServerAutoShutdownTask(String message) implements ExecOnceTask {

	public static final Codec<ServerAutoShutdownTask> CODEC = Codec.STRING.xmap(ServerAutoShutdownTask::new, ServerAutoShutdownTask::message);

	@Override
	public void execute(MinecraftServer server) {
		System.out.println(this.message);
		server.close();
	}

	@Override
	public Codec<? extends Task> codec() {
		return CODEC;
	}
}
