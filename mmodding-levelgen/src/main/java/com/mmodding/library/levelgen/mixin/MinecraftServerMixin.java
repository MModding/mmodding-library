package com.mmodding.library.levelgen.mixin;

import com.mmodding.library.levelgen.impl.seed.LevelSeedsStorage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

	@Shadow
	public abstract SavedDataStorage getDataStorage();

	@Inject(method = "createLevels", at = @At("HEAD"))
	private void createTaskManager(CallbackInfo ci) {
		this.getDataStorage().computeIfAbsent(LevelSeedsStorage.TYPE);
	}
}
