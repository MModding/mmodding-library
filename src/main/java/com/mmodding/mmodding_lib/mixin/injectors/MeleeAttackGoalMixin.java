package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.entities.goals.AdvancedMeleeAttackGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MeleeAttackGoal.class)
public class MeleeAttackGoalMixin {

	@ModifyConstant(method = "tick", constant = @Constant(doubleValue = 1.0))
	private double changeMinimalDistance(double constant) {
		if (((Object) this) instanceof AdvancedMeleeAttackGoal goal) {
			return goal.getMinimalDistance();
		}
		else {
			return constant;
		}
	}
}
