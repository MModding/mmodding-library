package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.library.caches.CacheAccess;
import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.StaticConfig;
import com.mmodding.mmodding_lib.networking.server.ServerOperations;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.gen.GeneratorOptions;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;

@ApiStatus.Internal
public class Events {

	private static void serverLoad(MinecraftServer server, ServerWorld world) {
		GeneratorOptions generatorOptions = server.getSaveProperties().getGeneratorOptions();
		GeneratorOptionsDuckInterface ducked = (GeneratorOptionsDuckInterface) generatorOptions;
		PersistentStateManager stateManager = world.getPersistentStateManager();
		stateManager.getOrCreate(ducked::mmodding_lib$stateFromNbt, ducked::mmodding_lib$createDifferedSeedsState, "differedSeedsState");
	}

	private static void serverInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
		MModdingLib.CONFIGS.forEach((qualifier, config) -> {
			if (config.getNetworkingSate() == Config.NetworkingState.LOCAL_CACHES) {
				LocalCaches.CONFIGS.put(qualifier, StaticConfig.of(config));
			}
		});

		if (MModdingLib.MMODDING_LIBRARY_CONFIG.getContent().getBoolean("showMModdingLibraryLocalCaches")) {
			Caches.LOCAL.forEach(CacheAccess::debugCache);
		}

		if (server.isDedicated()) {
			ServerOperations.sendConfigsToClient(handler.getPlayer());
			ServerOperations.sendGlintPacksToClient(handler.getPlayer());
		}
	}

	private static void serverDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
		Caches.LOCAL.forEach(CacheAccess::clearCache);
	}

	public static void register() {
		ServerWorldLoadEvents.LOAD.register(Events::serverLoad);
		ServerPlayConnectionEvents.INIT.register(Events::serverInit);
		ServerPlayConnectionEvents.DISCONNECT.register(Events::serverDisconnect);
	}
}
