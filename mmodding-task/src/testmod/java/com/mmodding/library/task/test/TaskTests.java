package com.mmodding.library.task.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.task.api.Task;
import com.mmodding.library.task.api.TaskRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.minecraft.resources.Identifier;

public class TaskTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager manager) {
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		TaskRegistry.register(createId("candies"), CandiesTask.CODEC);
		TaskRegistry.register(createId("server_auto_shutdown"), ServerAutoShutdownTask.CODEC);
		TaskRegistry.register(createId("diamond_giver"), DiamondGiverTask.CODEC);

		ServerLevelEvents.LOAD.register((server, _) -> {
			Task.schedule(server, new CandiesTask(10));
			Task.schedule(server, new ServerAutoShutdownTask("Why did you AFK on the testmod for so long..."), 20 * 3600);
		});

		ServerPlayerEvents.JOIN.register(player -> {
			Task.schedule(player.level().getServer(), new DiamondGiverTask(player.getUUID(), 2));
		});
	}

	public String namespace() {
		return "mmodding_task_testmod";
	}

	public Identifier createId(String path) {
		return Identifier.fromNamespaceAndPath(namespace(), path);
	}
}
