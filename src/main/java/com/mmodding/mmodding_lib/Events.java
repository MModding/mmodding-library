package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import com.mmodding.mmodding_lib.ducks.ServerWorldDuckInterface;
import com.mmodding.mmodding_lib.library.caches.CacheAccess;
import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.StaticConfig;
import com.mmodding.mmodding_lib.networking.server.ServerOperations;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.GeneratorOptions;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.command.api.QuiltCommandRegistrationEnvironment;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;

import java.util.Objects;

@ApiStatus.Internal
public class Events {

	private static void commandRegistration(CommandDispatcher<ServerCommandSource> dispatcher, CommandBuildContext context, QuiltCommandRegistrationEnvironment environment) {
		MModdingCommand.register(dispatcher);
	}

	private static void serverLoad(MinecraftServer server, ServerWorld world) {
		GeneratorOptions generatorOptions = server.getSaveProperties().getGeneratorOptions();
		GeneratorOptionsDuckInterface duckedOptions = (GeneratorOptionsDuckInterface) generatorOptions;
		Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager().getOrCreate(
			duckedOptions::mmodding_lib$differedSeedsStateFromNbt,
			duckedOptions::mmodding_lib$createDifferedSeedsState,
			"differed_seeds"
		);
		ServerWorldDuckInterface duckedWorld = (ServerWorldDuckInterface) world;
		world.getPersistentStateManager().getOrCreate(
			duckedWorld::mmodding_lib$stellarStatusesFromNbt,
			duckedWorld::mmodding_lib$createStellarStatuses,
			"stellar_statuses"
		);
	}

	private static void serverInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
		MModdingLib.CONFIGS.forEach((qualifier, config) -> {
			if (config.getNetworkingSate() == Config.NetworkingState.LOCAL_CACHES) {
				LocalCaches.CONFIGS.put(qualifier, StaticConfig.of(config));
			}
		});

		if (MModdingLib.LIBRARY_CONFIG.getContent().getBoolean("showMModdingLibraryLocalCaches")) {
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
		CommandRegistrationCallback.EVENT.register(Events::commandRegistration);
		ServerWorldLoadEvents.LOAD.register(Events::serverLoad);
		ServerPlayConnectionEvents.INIT.register(Events::serverInit);
		ServerPlayConnectionEvents.DISCONNECT.register(Events::serverDisconnect);
	}
}
