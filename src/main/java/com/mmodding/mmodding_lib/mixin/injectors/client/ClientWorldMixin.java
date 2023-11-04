package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.ducks.ClientWorldDuckInterface;
import com.mmodding.mmodding_lib.library.utils.ObjectUtils;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import com.mmodding.mmodding_lib.mixin.injectors.WorldMixin;
import com.mmodding.mmodding_lib.states.readable.ReadableStellarStatuses;
import net.minecraft.client.world.ClientWorld;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends WorldMixin implements WorldUtils.TickTaskClient, ClientWorldDuckInterface {

	@Unique
	private List<MutablePair<Long, Runnable>> tasks;

	@Unique
	private List<MutablePair<Long, Runnable>> repeatingTasks;

	@Unique
	private List<MutablePair<MutableTriple<Integer, Long, Runnable>, Integer>> eachTasks;

	@Unique
	protected ReadableStellarStatuses stellarStatuses = this.fillStellarStatuses(ReadableStellarStatuses.of(new HashMap<>()));

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {

		for (int i = 0; i < ObjectUtils.ifNullMakeDefault(this.tasks, () -> this.tasks = new ArrayList<>()).size(); i++) {
			MutablePair<Long, Runnable> task = this.tasks.get(i);

			task.setLeft(task.getLeft() - 1);

			if (task.getLeft() <= 0L) {
				task.getRight().run();

				this.tasks.remove(i);
				i--;
			}
		}

		for (int i = 0; i < ObjectUtils.ifNullMakeDefault(this.repeatingTasks, () -> this.repeatingTasks = new ArrayList<>()).size(); i++) {
			MutablePair<Long, Runnable> task = this.repeatingTasks.get(i);

			task.setLeft(task.getLeft() - 1);
			task.getRight().run();

			if (task.getLeft() <= 0L) {
				this.repeatingTasks.remove(i);
				i--;
			}
		}

		for (int i = 0 ; i < ObjectUtils.ifNullMakeDefault(this.eachTasks, () -> this.eachTasks = new ArrayList<>()).size(); i++) {
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

	@Override
	public void mmodding_lib$doTaskAfter(long ticksToWait, Runnable run) {
		ObjectUtils.ifNullMakeDefault(this.tasks, () -> this.tasks = new ArrayList<>()).add(new MutablePair<>(ticksToWait, run));
	}

	@Override
	public void mmodding_lib$repeatTaskUntil(long ticksUntil, Runnable run) {
		ObjectUtils.ifNullMakeDefault(this.repeatingTasks, () -> this.repeatingTasks = new ArrayList<>()).add(new MutablePair<>(ticksUntil, run));
	}

	@Override
	public void mmodding_lib$repeatTaskEachTimeUntil(int ticksBetween, long ticksUntil, Runnable run) {
		ObjectUtils.ifNullMakeDefault(this.eachTasks, () -> this.eachTasks = new ArrayList<>()).add(new MutablePair<>(new MutableTriple<>(ticksBetween, ticksUntil, run), 0));
	}

	@Override
	public ReadableStellarStatuses mmodding_lib$getStellarStatusesAccess() {
		return this.stellarStatuses;
	}
}
