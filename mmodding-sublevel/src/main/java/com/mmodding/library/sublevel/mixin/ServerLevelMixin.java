package com.mmodding.library.sublevel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.sublevel.impl.ServerSublevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;

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
}
