package com.mmodding.library.task.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.task.api.Task;
import com.mmodding.library.task.api.PersistentTaskRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.BlockEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;

public class TaskTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager manager) {
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		PersistentTaskRegistry.register(createId("candies"), CandiesTask.CODEC);
		PersistentTaskRegistry.register(createId("diamond_giver"), DiamondGiverTask.CODEC);

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			Task.schedule(server, new CandiesTask(10));
			Task.schedule(server, new ServerAutoShutdownTask("Why did you AFK on the testmod for so long..."), 20 * 3600);
		});

		BlockEvents.USE_WITHOUT_ITEM.register((state, _, _, player, _) -> {
			if (player instanceof ServerPlayer serverPlayer) {
				if (state.is(Blocks.DIAMOND_BLOCK)) {
					Task.schedule(serverPlayer.level().getServer(), new DiamondGiverTask(serverPlayer.getUUID(), 3));
					return InteractionResult.SUCCESS_SERVER;
				}
			}
			return null;
		});
	}

	public String namespace() {
		return "mmodding_task_testmod";
	}

	public Identifier createId(String path) {
		return Identifier.fromNamespaceAndPath(namespace(), path);
	}
}
