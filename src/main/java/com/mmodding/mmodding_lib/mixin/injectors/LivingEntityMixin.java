package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.LivingEntityDuckInterface;
import com.mmodding.mmodding_lib.networking.CommonOperations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements LivingEntityDuckInterface {

	@Unique
	private Map<Integer, Identifier> stuckArrowTypes = new HashMap<>();

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
	private void tick(CallbackInfo ci) {
		this.deleteStuckArrowType();
	}

	@Override
	public Map<Integer, Identifier> mmodding_lib$getStuckArrowTypes() {
		return new HashMap<>(this.stuckArrowTypes);
	}

	@Override
	public void mmodding_lib$setStuckArrowTypes(Map<Integer, Identifier> stuckArrowTypes) {
		this.stuckArrowTypes = stuckArrowTypes;
		this.syncStuckArrowTypes();
	}

	@Override
	public void mmodding_lib$putStuckArrowType(int index, Identifier arrowEntityId) {
		this.stuckArrowTypes.put(index, arrowEntityId);
		this.syncStuckArrowTypes();
	}

	@Unique
	private void deleteStuckArrowType() {
		Map<Integer, Identifier> oldStuckArrowTypes = this.mmodding_lib$getStuckArrowTypes();
		Map<Integer, Identifier> newStuckArrowTypes = new HashMap<>();
		int smallest = !oldStuckArrowTypes.isEmpty() ? Integer.MAX_VALUE : 0;
		for (int current : oldStuckArrowTypes.keySet()) {
			smallest = Math.min(smallest, current);
		}
		oldStuckArrowTypes.remove(smallest);
		oldStuckArrowTypes.forEach((index, arrowEntityId) -> newStuckArrowTypes.put(index - 1, arrowEntityId));
		this.mmodding_lib$setStuckArrowTypes(newStuckArrowTypes);
	}

	@Unique
	private void syncStuckArrowTypes() {
		if (!this.world.isClient()) {
			this.world.getPlayers().forEach(playerEntity -> CommonOperations.sendLivingEntityStuckArrowTypesToClient(
				(LivingEntity) (Object) this, this.stuckArrowTypes, (ServerPlayerEntity) playerEntity
			));
		}
	}
}
