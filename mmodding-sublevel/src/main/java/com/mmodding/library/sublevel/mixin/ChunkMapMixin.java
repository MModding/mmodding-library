package com.mmodding.library.sublevel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.sublevel.impl.ServerSublevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
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
}
