package com.mmodding.mmodding_lib.library.entities.action;

import com.mmodding.mmodding_lib.library.client.utils.AnimationUtils;
import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class EntityAction {

	private final Entity entity;
	private final SyncableData<Integer> trackingTime;
	private final int timeBeforeExecution;
	private final int delayAfterExecution;

	@Environment(EnvType.CLIENT)
	private final AnimationState state = new AnimationState();

	@Nullable
	private Runnable action = null;

	public EntityAction(Entity entity, Identifier action, int timeBeforeExecution, int delayAfterExecution) {
		this.entity = entity;
		this.trackingTime = new SyncableData<>(-1, entity, action, TrackedDataHandlerRegistry.INTEGER);
		this.timeBeforeExecution = timeBeforeExecution;
		this.delayAfterExecution = delayAfterExecution;
	}

	public boolean isExecutingAction() {
		return this.trackingTime.get() != -1;
	}

	public void execute(Runnable action) {
		if (this.entity.getWorld() instanceof ServerWorld) {
			this.action = action;
			this.trackingTime.set(0);
			this.trackingTime.synchronize();
		}
		else {
			throw new IllegalStateException("Executed on a wrong Environment Side");
		}
	}

	public void tick() {
		if (this.entity.getWorld() instanceof ServerWorld) {
			this.serverTick();
		}
		else {
			this.clientTick();
		}
	}

	private int getFullTime() {
		return this.timeBeforeExecution + this.delayAfterExecution;
	}

	private void serverTick() {
		if (this.trackingTime.get() >= 0 && this.trackingTime.get() <= this.getFullTime()) {
			if (this.trackingTime.get() == this.timeBeforeExecution) {
				assert this.action != null;
				this.action.run();
				this.action = null;
			}
			this.trackingTime.set(this.trackingTime.get() + 1);
			if (this.trackingTime.get() % 200 == 0) {
				this.trackingTime.synchronize();
			}
		}
		else if (this.trackingTime.get() >= 0 && this.trackingTime.get() > this.getFullTime()) {
			this.trackingTime.set(-1);
			this.trackingTime.synchronize();
		}
	}

	@Environment(EnvType.CLIENT)
	private void clientTick() {
		if (this.trackingTime.get() >= 0 && this.trackingTime.get() <= this.getFullTime()) {
			if (this.trackingTime.get() == 0) {
				// We need to use AnimationState#restart and not AnimationState#start, because even
				// when an animation is not looping, after the animation visually finished to animate,
				// it is not the case in the code, preventing AnimationState#start to work properly.
				this.state.restart(this.entity.age);
			}
			this.trackingTime.set(this.trackingTime.get() + 1);
		}
		else if (this.trackingTime.get() >= 0 && this.trackingTime.get() > this.getFullTime()) {
			this.trackingTime.set(-1);
			this.state.stop();
		}
	}

	@Environment(EnvType.CLIENT)
	public AnimationState getAnimationState() {
		return this.state;
	}
}
