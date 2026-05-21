package com.mmodding.library.task.test;

import com.mmodding.library.task.api.MultiStepTask;
import com.mmodding.library.task.api.Task;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;

public record CandiesTask(int candies) implements MultiStepTask {

	public static final Codec<CandiesTask> CODEC = Codec.INT.xmap(CandiesTask::new, CandiesTask::candies);

	@Override
	public Redirector execute(MinecraftServer server, int step) {
		return switch (step) {
			case 0 -> new Redirector(1, 20);
			case 1 -> {
				System.out.println("I want to eat " + this.candies + " candies!");
				yield new Redirector(2, 60);
			}
			default -> {
				System.out.println("I ate all of them.");
				yield Redirector.END;
			}
		};
	}

	@Override
	public Codec<? extends Task> codec() {
		return CODEC;
	}
}
