package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.blocks.CustomPointedDripstoneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@Inject(method = "method_33278", at = @At("TAIL"), cancellable = true)
	private static void method_33278(Direction direction, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof CustomPointedDripstoneBlock) {
			cir.setReturnValue(state.get(Properties.VERTICAL_DIRECTION) == direction);
		}
	}

	@Inject(method = "method_33275", at = @At("TAIL"), cancellable = true)
	private static void method_33275(Direction direction, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof CustomPointedDripstoneBlock) {
			cir.setReturnValue(state.get(Properties.VERTICAL_DIRECTION) == direction);
		}
	}

	@Inject(method = "method_33281", at = @At("TAIL"), cancellable = true)
	private static void method_33281(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof CustomPointedDripstoneBlock) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "isTip(Lnet/minecraft/block/BlockState;Z)Z", at = @At("HEAD"), cancellable = true)
	private static void isTip(BlockState state, boolean allowMerged, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof CustomPointedDripstoneBlock) {
			Thickness thickness = state.get(Properties.THICKNESS);
			cir.setReturnValue(thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE);
		}
	}

	@Inject(method = "isHeldByPointedDripstone", at = @At("TAIL"), cancellable = true)
	private static void isHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (world.getBlockState(pos.up()).getBlock() instanceof CustomPointedDripstoneBlock) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "isPointedDripstoneFacingDirection", at = @At("TAIL"), cancellable = true)
	private static void isPointedDripstoneFacingDirection(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof CustomPointedDripstoneBlock) {
			cir.setReturnValue(state.get(Properties.VERTICAL_DIRECTION) == direction);
		}
	}
}
