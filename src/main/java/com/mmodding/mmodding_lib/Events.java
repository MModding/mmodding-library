package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.gen.GeneratorOptions;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;

@ApiStatus.Internal
public class Events {

	public static void serverLoad(MinecraftServer server, ServerWorld world) {
		GeneratorOptions generatorOptions = server.getSaveProperties().getGeneratorOptions();
		GeneratorOptionsDuckInterface ducked = (GeneratorOptionsDuckInterface) generatorOptions;
		PersistentStateManager stateManager = world.getPersistentStateManager();
		stateManager.getOrCreate(ducked::stateFromNbt, ducked::createDifferedSeedsState, "differedSeedsState");
	}

	public static void register() {
		ServerWorldLoadEvents.LOAD.register(Events::serverLoad);
	}
}
