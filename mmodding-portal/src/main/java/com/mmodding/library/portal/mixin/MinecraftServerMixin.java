package com.mmodding.library.portal.mixin;

import com.mmodding.library.portal.impl.storage.PortalNodeStorage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements PortalNodeStorage.Duck {

	@Unique
	private PortalNodeStorage portalNodeStorage;

	@Shadow
	public abstract SavedDataStorage getDataStorage();

	@Inject(method = "createLevels", at = @At("HEAD"))
	private void createTaskManager(CallbackInfo ci) {
		this.portalNodeStorage = this.getDataStorage().computeIfAbsent(PortalNodeStorage.TYPE);
	}

	@Override
	public PortalNodeStorage mmodding$getPortalNodeStorage() {
		return this.portalNodeStorage;
	}
}
