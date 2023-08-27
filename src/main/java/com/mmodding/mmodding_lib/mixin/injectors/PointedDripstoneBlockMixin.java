package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.blocks.CustomPointedDripstoneBlock;
import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.fluid.Fluid;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@Inject(method = "createParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/Fluid;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/Fluid;isIn(Lnet/minecraft/tag/TagKey;)Z"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
	private static void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfo ci, Vec3d vec3d, double d, double e, double f, double g, Fluid fluid2) {
		if (fluid2 instanceof FluidExtensions extensions) {
			world.addParticle(extensions.getDrippingParticle(), e, f, g, 0.0f, 0.0f, 0.0f);
			ci.cancel();
		}
	}

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
