package com.mmodding.library.sublevel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.sublevel.impl.ServerSublevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;

@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin {

	@Shadow
	public abstract Level getLevel();

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;getDimensionPath(Lnet/minecraft/resources/ResourceKey;)Ljava/nio/file/Path;"))
	private Path applySubLevelPaths(LevelStorageSource.LevelStorageAccess instance, ResourceKey<Level> name, Operation<Path> original) {
		Path result = original.call(instance, name);
		if (this.getLevel() instanceof ServerSublevel<?> sub) {
			result = result.resolve(sub.getMappedAttachment());
		}
		return result;
	}
}
