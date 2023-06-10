package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.tuple.MutablePair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements WorldUtils.TickTask {

	@Unique
	private final List<MutablePair<Long, Runnable>> tasks = new ArrayList<>();

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {

		for (int i = 0; i < this.tasks.size(); i++) {
			MutablePair<Long, Runnable> task = tasks.get(i);

			task.setLeft(task.getLeft() - 1);
			if (task.getLeft() <= 0L) {
				task.getRight().run();

				tasks.remove(i);
				i--;
			}
		}
	}

	@Override
	public void doTaskAfter(long ticksUntil, Runnable run) {
		tasks.add(new MutablePair<>(ticksUntil, run));
	}
}
