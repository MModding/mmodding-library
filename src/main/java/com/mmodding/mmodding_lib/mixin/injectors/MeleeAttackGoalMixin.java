package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.entities.goals.AdvancedMeleeAttackGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MeleeAttackGoal.class)
public class MeleeAttackGoalMixin {

	@Shadow
	@Final
	protected PathAwareEntity mob;

	@Shadow
	private double targetX;

	@Shadow
	private double targetY;

	@Shadow
	private double targetZ;

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/pathing/EntityNavigation;startMovingTo(Lnet/minecraft/entity/Entity;D)Z"))
	private boolean changeMinimalDistance(EntityNavigation instance, Entity entity, double speed, Operation<Boolean> original) {
		if (((Object) this) instanceof AdvancedMeleeAttackGoal goal) {
			double squaredDistance = this.mob.squaredDistanceTo(this.targetX, this.targetY, this.targetZ);
			if (squaredDistance >= goal.getMinimalDistance()) {
				return original.call(instance, entity, speed);
			}
			else {
				return false;
			}
		}
		else {
			return original.call(instance, entity, speed);
		}
	}
}
