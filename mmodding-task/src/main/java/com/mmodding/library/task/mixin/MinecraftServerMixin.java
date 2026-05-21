package com.mmodding.library.task.mixin;

import com.mmodding.library.task.impl.InternalTaskManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements InternalTaskManager.Duck {

	@Unique
	private InternalTaskManager taskManager;

	@Shadow
	public abstract SavedDataStorage getDataStorage();

	@Inject(method = "createLevels", at = @At("HEAD"))
	private void createTaskManager(CallbackInfo ci) {
		this.taskManager = this.getDataStorage().computeIfAbsent(InternalTaskManager.TYPE);
	}

	@Inject(method = "tickServer", at = @At("TAIL"))
	private void tickTaskManager(BooleanSupplier haveTime, CallbackInfo ci) {
		this.taskManager.tick((MinecraftServer) (Object) this);
	}

	@Override
	public InternalTaskManager mmodding_library$manager() {
		return this.taskManager;
	}
}
