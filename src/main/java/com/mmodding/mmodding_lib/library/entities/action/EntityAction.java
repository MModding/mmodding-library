package com.mmodding.mmodding_lib.library.entities.action;

import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class EntityAction {

	private final Entity entity;
	private final SyncableData<Integer> remainingTime;
	private final int delayBeforeStaring;
	private final int delayAfterEnding;

	@ClientOnly
	private final AnimationState state = new AnimationState();

	@Nullable
	private Runnable action = null;

	public EntityAction(Entity entity, Identifier action, int delayBeforeStarting, int delayAfterEnding) {
		this.entity = entity;
		this.remainingTime = new SyncableData<>(entity, action, TrackedDataHandlerRegistry.INTEGER);
		this.delayBeforeStaring = delayBeforeStarting;
		this.delayAfterEnding = delayAfterEnding;
	}

	public boolean isExecutingAction() {
		return this.remainingTime.get() != -1;
	}

	public void execute(Runnable action) {
		if (this.entity.getWorld() instanceof ServerWorld) {
			this.action = action;
			this.remainingTime.set(0);
			this.remainingTime.synchronize();
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

	private void serverTick() {
		if (this.remainingTime.get() >= 0 && this.remainingTime.get() <= this.delayBeforeStaring + this.delayAfterEnding) {
			if (this.remainingTime.get() == this.delayBeforeStaring) {
				assert this.action != null;
				this.action.run();
				this.action = null;
			}
			this.remainingTime.set(this.remainingTime.get() + 1);
			if (this.remainingTime.get() % 200 == 0) {
				this.remainingTime.synchronize();
			}
		}
		else if (this.remainingTime.get() >= 0 && this.remainingTime.get() > this.delayBeforeStaring + this.delayAfterEnding) {
			this.remainingTime.set(-1);
			this.remainingTime.synchronize();
		}
	}

	@ClientOnly
	private void clientTick() {
		if (this.remainingTime.get() >= 0 && this.remainingTime.get() <= this.delayBeforeStaring + this.delayAfterEnding) {
			if (this.remainingTime.get() == 0) {
				this.state.start(this.entity.age);
			}
			this.remainingTime.set(this.remainingTime.get() + 1);
		}
		else if (this.remainingTime.get() >= 0 && this.remainingTime.get() > this.delayBeforeStaring + this.delayAfterEnding) {
			this.remainingTime.set(-1);
			this.state.stop();
		}
	}

	@ClientOnly
	public AnimationState getAnimationState() {
		return this.state;
	}
}
