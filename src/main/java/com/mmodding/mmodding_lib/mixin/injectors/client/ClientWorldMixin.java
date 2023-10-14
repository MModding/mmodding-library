package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import com.mmodding.mmodding_lib.mixin.injectors.WorldMixin;
import net.minecraft.client.world.ClientWorld;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends WorldMixin implements WorldUtils.TickTaskClient {

	@Unique
	private final List<MutablePair<Long, Runnable>> tasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<Long, Runnable>> repeatingTasks = new ArrayList<>();

	@Unique
	private final List<MutablePair<MutableTriple<Integer, Long, Runnable>, Integer>> eachTasks = new ArrayList<>();

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {

		for (int i = 0; i < this.tasks.size(); i++) {
			MutablePair<Long, Runnable> task = this.tasks.get(i);

			task.setLeft(task.getLeft() - 1);

			if (task.getLeft() <= 0L) {
				task.getRight().run();

				this.tasks.remove(i);
				i--;
			}
		}

		for (int i = 0; i < this.repeatingTasks.size(); i++) {
			MutablePair<Long, Runnable> task = this.repeatingTasks.get(i);

			task.setLeft(task.getLeft() - 1);
			task.getRight().run();

			if (task.getLeft() <= 0L) {
				this.repeatingTasks.remove(i);
				i--;
			}
		}

		for (int i = 0 ; i < this.eachTasks.size(); i++) {
			MutablePair<MutableTriple<Integer, Long, Runnable>, Integer> task = this.eachTasks.get(i);

			MutableTriple<Integer, Long, Runnable> params = task.getLeft();

			params.setMiddle(params.getMiddle() - 1);
			task.setRight(task.getRight() + 1);

			if (task.getRight() >= params.getLeft()) {
				params.getRight().run();
				task.setRight(0);
			}

			if (params.getMiddle() <= 0L) {
				this.eachTasks.remove(i);
				i--;
			}
		}
	}

	@Inject(method = "tickTime", at = @At(value = "TAIL"))
	private void tickTime(CallbackInfo ci) {
		this.allStellarStatus.forEach((key, value) -> value.tick());
	}

	@Override
	public void mmodding_lib$doTaskAfter(long ticksToWait, Runnable run) {
		this.tasks.add(new MutablePair<>(ticksToWait, run));
	}

	@Override
	public void mmodding_lib$repeatTaskUntil(long ticksUntil, Runnable run) {
		this.repeatingTasks.add(new MutablePair<>(ticksUntil, run));
	}

	@Override
	public void mmodding_lib$repeatTaskEachTimeUntil(int ticksBetween, long ticksUntil, Runnable run) {
		this.eachTasks.add(new MutablePair<>(new MutableTriple<>(ticksBetween, ticksUntil, run), 0));
	}
}
