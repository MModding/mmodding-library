package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements WorldUtils.TickTask {

	@Unique
	private long ticksToWait;

	@Unique
	private Runnable task;

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		if (this.ticksToWait-- <= 0L) {
			this.task.run();
		}
	}

	@Override
	public void doTaskAfter(long ticksUntil, Runnable run) {
		this.ticksToWait = ticksUntil;
		this.task = run;
	}
}
