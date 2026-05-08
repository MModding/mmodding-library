package com.mmodding.library.sublevel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.sublevel.impl.LimitedLevelSource;
import com.mmodding.library.sublevel.impl.ServerSublevel;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;getDimensionPath(Lnet/minecraft/resources/ResourceKey;)Ljava/nio/file/Path;"))
	private Path applySubLevelPaths(LevelStorageSource.LevelStorageAccess instance, ResourceKey<Level> name, Operation<Path> original) {
		Path result = original.call(instance, name);
		if (((ServerLevel) (Object) this) instanceof ServerSublevel<?> limited) {
			result = result.resolve(limited.getMappedAttachment());
		}
		return result;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;getViewDistance()I"))
	private int clampSubLevelViewDistance(int original) {
		if (((ServerLevel) (Object) this) instanceof ServerSublevel<?> sub) {
			return Math.min(original, sub.getType().chunkSquareRadius() * 2);
		}
		else {
			return original;
		}
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;getSimulationDistance()I"))
	private int clampSubLevelSimulationDistance(int original) {
		if (((ServerLevel) (Object) this) instanceof ServerSublevel<?> sub) {
			return Math.min(original, sub.getType().chunkSquareRadius() * 2);
		}
		else {
			return original;
		}
	}

	@WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/chunk/ChunkGenerator;IIZLnet/minecraft/world/level/entity/ChunkStatusUpdateListener;Ljava/util/function/Supplier;)Lnet/minecraft/server/level/ServerChunkCache;"))
	private ServerChunkCache limitChunkGeneratorIfSublevel(ServerLevel level, LevelStorageSource.LevelStorageAccess levelStorage, DataFixer fixerUpper, StructureTemplateManager structureTemplateManager, Executor executor, ChunkGenerator generator, int viewDistance, int simulationDistance, boolean syncWrites, ChunkStatusUpdateListener chunkStatusListener, Supplier<SavedDataStorage> overworldDataStorage, Operation<ServerChunkCache> original) {
		return original.call(level, levelStorage, fixerUpper, structureTemplateManager, executor, level instanceof ServerSublevel<?> sub ? new LimitedLevelSource(generator, sub.getType().chunkSquareRadius()) : generator, viewDistance, simulationDistance, syncWrites, chunkStatusListener, overworldDataStorage);
	}
}
