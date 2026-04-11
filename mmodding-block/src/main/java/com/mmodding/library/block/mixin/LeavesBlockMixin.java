package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LeavesBlock;registerDefaultState(Lnet/minecraft/world/level/block/state/BlockState;)V"))
	private void removeConditionally(LeavesBlock instance, BlockState blockState, Operation<Void> original) {
		if (!(instance instanceof AdvancedLeavesBlock)) {
			original.call(instance, blockState);
		}
	}

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
	private Object removeConditionally(BlockState instance, Property<?> property, Comparable<?> comparable, Operation<Object> original) {
		return ((Object) this) instanceof AdvancedLeavesBlock ? instance : original.call(instance, property, comparable);
	}

	@WrapOperation(method = "getOptionalDistanceAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"))
	private static <T extends Comparable<T>> T preventWrongProperties(BlockState instance, Property<T> property, Operation<T> original) {
		if (instance.getBlock() instanceof AdvancedLeavesBlock leaves) {
			return original.call(instance, leaves.getDistanceProperty());
		}
		else {
			return original.call(instance, property);
		}
	}
}
