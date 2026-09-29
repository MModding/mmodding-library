package com.mmodding.library.sublevel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.sublevel.impl.LimitedLevelSource;
import com.mmodding.library.sublevel.impl.ServerSublevel;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;

@Mixin(ChunkMap.class)
public class ChunkMapMixin {

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;getDimensionPath(Lnet/minecraft/resources/ResourceKey;)Ljava/nio/file/Path;"))
	private static Path applySubLevelPaths(LevelStorageSource.LevelStorageAccess instance, ResourceKey<Level> name, Operation<Path> original, @Local(argsOnly = true, name = "level") ServerLevel level) {
		Path result = original.call(instance, name);
		if (level instanceof ServerSublevel<?> limited) {
			result = result.resolve(limited.getMappedAttachment());
		}
		return result;
	}

	// The LimitedLevelSource can't pass the instance check, so we just pass it to the delegate
	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/RandomState;create(Lnet/minecraft/core/HolderGetter;JZLnet/minecraft/world/level/block/state/BlockState;ILnet/minecraft/world/level/levelgen/NoiseRouter;)Lnet/minecraft/world/level/levelgen/RandomState;"))
	private static RandomState applyForDelegated(HolderGetter<NormalNoise> noises, long seed, boolean useLegacyRandom, BlockState defaultBlock, int seaLevel, NoiseRouter noiseRouter, Operation<RandomState> original, @Local(argsOnly = true, name = "generator") ChunkGenerator generator) {
		if (generator instanceof LimitedLevelSource limited && limited.getDelegate() instanceof NoiseBasedChunkGenerator based) {
			return RandomState.create(noises, seed, based.generatorSettings().value());
		}
		else {
			return original.call(noises, seed, useLegacyRandom, defaultBlock, seaLevel, noiseRouter);
		}
	}
}
