package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.ducks.LivingEntityDuckInterface;
import com.mmodding.mmodding_lib.library.client.render.entity.animation.DeathAnimation;
import com.mmodding.mmodding_lib.library.entities.data.MModdingTrackedDataHandlers;
import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.ObjectUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements LivingEntityDuckInterface {

	@Unique
	private final SyncableData<List<Identifier>> stuckArrowTypes = new SyncableData<>(
		new ArrayList<>(),
		(LivingEntity) (Object) this,
		new MModdingIdentifier("stuck_arrow_types"),
		MModdingTrackedDataHandlers.IDENTIFIER_LIST
	);

	@Shadow
	public int deathTime;

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
	private void tick(CallbackInfo ci) {
		this.deleteStuckArrowType();
	}

	@WrapOperation(method = "updatePostDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V"))
	private void conditionallyCancelDeathAnimationFirstPart(World instance, Entity entity, byte status, Operation<Void> original) {
		if (!(entity instanceof DeathAnimation animation) || animation.getDeathAnimation() == null) {
			original.call(instance, entity, status);
		}
	}

	@WrapOperation(method = "updatePostDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;remove(Lnet/minecraft/entity/Entity$RemovalReason;)V"))
	private void conditionallyCancelDeathAnimationSecondPart(LivingEntity instance, Entity.RemovalReason removalReason, Operation<Void> original) {
		if (!(instance instanceof DeathAnimation animation) || animation.getDeathAnimation() == null) {
			original.call(instance, removalReason);
		}
	}

	@Inject(method = "updatePostDeath", at = @At(value = "TAIL"))
	private void updateDeath(CallbackInfo ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (livingEntity instanceof DeathAnimation deathAnimation) {
			if (deathAnimation.getDeathAnimation() != null && this.deathTime - 1 == 0) {
				deathAnimation.getDeathAnimation().run();
			}
			if (this.deathTime == deathAnimation.getDeathTime() && !this.getWorld().isClient()) {
				this.world.sendEntityStatus(livingEntity, EntityStatuses.ADD_DEATH_PARTICLES);
				this.remove(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	public List<Identifier> mmodding_lib$getStuckArrowTypes() {
		return new ArrayList<>(this.stuckArrowTypes.get());
	}

	@Override
	public void mmodding_lib$setStuckArrowTypes(List<Identifier> stuckArrowTypes) {
		this.stuckArrowTypes.set(stuckArrowTypes);
		this.stuckArrowTypes.synchronize();
	}

	@Override
	public void mmodding_lib$addStuckArrowType(Identifier arrowEntityId) {
		this.stuckArrowTypes.get().add(arrowEntityId);
		this.stuckArrowTypes.synchronize();
	}

	@Unique
	private void deleteStuckArrowType() {
		List<Identifier> stuckArrowTypes = this.mmodding_lib$getStuckArrowTypes();
		stuckArrowTypes.remove(stuckArrowTypes.size() - 1);
		this.mmodding_lib$setStuckArrowTypes(stuckArrowTypes);
	}

	static {
		ObjectUtils.load(MModdingTrackedDataHandlers.IDENTIFIER_LIST);
	}
}
