package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.MModdingDamageSources;
import com.mmodding.mmodding_lib.library.worldgen.veins.CustomVeinType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WorldUtils {

	public static void addLocalCommonLootToStructurePiece(Identifier structure, ItemStack... stacks) {
		MModdingGlobalMaps.LOCAL_COMMON_STRUCTURE_PIECE_LOOT.compute(structure, (key, itemStacks) -> {
			if (itemStacks == null) {
				return List.of(stacks);
			}
			else {
				itemStacks.addAll(List.of(stacks));
				return itemStacks;
			}
		});
	}

	public static void addDifferedSeed(Identifier dimensionIdentifier) {
		MModdingGlobalMaps.DIFFERED_DIMENSION_SEEDS.add(RegistryKey.of(Registry.WORLD_KEY, dimensionIdentifier));
	}

	public static void addCustomVeinTypes(Identifier chunkGeneratorSettingsIdentifier, CustomVeinType... customVeinTypes) {
		MModdingGlobalMaps.CUSTOM_VEIN_TYPES.put(chunkGeneratorSettingsIdentifier, List.of(customVeinTypes));
	}

	public static void doSyncedTaskAfter(World world, long ticksToWait, Runnable task) {
		if (!world.isClient()) {
			WorldUtils.doTaskAfter((ServerWorld) world, ticksToWait, task);
		}
		else {
			WorldUtils.doTaskAfter((ClientWorld) world, ticksToWait, task);
		}
	}

	public static void repeatSyncedTaskUntil(World world, long ticksUntil, Runnable task) {
		if (!world.isClient()) {
			WorldUtils.repeatTaskUntil((ServerWorld) world, ticksUntil, task);
		}
		else {
			WorldUtils.repeatTaskUntil((ClientWorld) world, ticksUntil, task);
		}
	}

	public static void repeatSyncedTaskEachTimeUntil(World world, int ticksBetween, long ticksUntil, Runnable task) {
		if (!world.isClient()) {
			WorldUtils.repeatTaskEachTimeUntil((ServerWorld) world, ticksBetween, ticksUntil, task);
		}
		else {
			WorldUtils.repeatTaskEachTimeUntil((ClientWorld) world, ticksBetween, ticksUntil, task);
		}
	}

	public static void doTaskAfter(ServerWorld serverWorld, long ticksToWait, Runnable task) {
		((TickTaskServer) serverWorld).mmodding_lib$doTaskAfter(ticksToWait, task);
	}

	public static void repeatTaskUntil(ServerWorld serverWorld, long ticksUntil, Runnable task) {
		((TickTaskServer) serverWorld).mmodding_lib$repeatTaskUntil(ticksUntil, task);
	}

	public static void repeatTaskEachTimeUntil(ServerWorld serverWorld, int ticksBetween, long ticksUntil, Runnable task) {
		((TickTaskServer) serverWorld).mmodding_lib$repeatTaskEachTimeUntil(ticksBetween, ticksUntil, task);
	}

	public static void doTaskAfter(ClientWorld clientWorld, long ticksToWait, Runnable task) {
		((TickTaskClient) clientWorld).mmodding_lib$doTaskAfter(ticksToWait, task);
	}

	public static void repeatTaskUntil(ClientWorld clientWorld, long ticksUntil, Runnable task) {
		((TickTaskClient) clientWorld).mmodding_lib$repeatTaskUntil(ticksUntil, task);
	}

	public static void repeatTaskEachTimeUntil(ClientWorld clientWorld, int ticksBetween, long ticksUntil, Runnable task) {
		((TickTaskClient) clientWorld).mmodding_lib$repeatTaskEachTimeUntil(ticksBetween, ticksUntil, task);
	}

	public static void pushExplosion(WorldAccess world, BlockPos pos, float power) {
		Explosion explosion = new Explosion((World) world, null, MModdingDamageSources.PUSH, null, pos.getX(), pos.getY(), pos.getZ(), power, false, Explosion.DestructionType.NONE);
		explosion.collectBlocksAndDamageEntities();
		explosion.affectWorld(false);
	}

	public static Set<ServerPlayerEntity> getPlayersAround(ServerWorld serverWorld, Vec3d pos, Predicate<Double> length) {
		return serverWorld.getPlayers()
			.stream()
			.filter(player -> length.test(player.getPos().subtract(pos).length()))
			.collect(Collectors.toSet());
	}

	public interface TickTaskServer {

		void mmodding_lib$doTaskAfter(long ticksToWait, Runnable run);

		void mmodding_lib$repeatTaskUntil(long ticksUntil, Runnable run);

		void mmodding_lib$repeatTaskEachTimeUntil(int ticksBetween, long ticksUntil, Runnable run);
	}

	public interface TickTaskClient {

		void mmodding_lib$doTaskAfter(long ticksToWait, Runnable run);

		void mmodding_lib$repeatTaskUntil(long ticksUntil, Runnable run);

		void mmodding_lib$repeatTaskEachTimeUntil(int ticksBetween, long ticksUntil, Runnable run);
	}
}
