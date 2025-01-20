package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.blocks.CustomLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LeavesBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
	private void removeConditionally(LeavesBlock instance, BlockState blockState, Operation<Void> original) {
		if (!(instance instanceof CustomLeavesBlock)) {
			original.call(instance, blockState);
		}
	}

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
	private Object removeConditionally(BlockState instance, Property<?> property, Comparable<?> comparable, Operation<Object> original) {
		return ((Object) this) instanceof CustomLeavesBlock ? instance : original.call(instance, property, comparable);
	}

	@WrapOperation(method = "getDistanceFromLog", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;get(Lnet/minecraft/state/property/Property;)Ljava/lang/Comparable;"))
	private static <T extends Comparable<T>> T preventWrongProperties(BlockState instance, Property<T> property, Operation<T> original) {
		if (instance.getBlock() instanceof CustomLeavesBlock leaves) {
			return original.call(instance, leaves.getDistanceProperty());
		}
		else {
			return original.call(instance, property);
		}
	}
}
