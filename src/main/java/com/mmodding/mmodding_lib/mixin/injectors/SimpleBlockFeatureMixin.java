package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.blocks.CustomDoubleWidthBlock;
import com.mmodding.mmodding_lib.library.math.OrientedBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.SimpleBlockFeature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleBlockFeature.class)
public class SimpleBlockFeatureMixin {

	@Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", shift = At.Shift.AFTER))
	private void fixDoubleWidth(FeatureContext<SimpleBlockFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
		StructureWorldAccess world = context.getWorld();
		BlockPos pos = context.getOrigin();
		BlockState state = context.getWorld().getBlockState(pos);
		if (state.getBlock() instanceof CustomDoubleWidthBlock) {
			if (!world.isClient()) {
				OrientedBlockPos oriented = OrientedBlockPos.of(pos).apply(state.get(CustomDoubleWidthBlock.FACING));
				world.setBlockState(oriented.front(), state.with(CustomDoubleWidthBlock.PART, CustomDoubleWidthBlock.DoubleWidthPart.SUB_PART_0), Block.NOTIFY_ALL);
				world.setBlockState(oriented.front().left(), state.with(CustomDoubleWidthBlock.PART, CustomDoubleWidthBlock.DoubleWidthPart.SUB_PART_1), Block.NOTIFY_ALL);
				world.setBlockState(oriented.left(), state.with(CustomDoubleWidthBlock.PART, CustomDoubleWidthBlock.DoubleWidthPart.SUB_PART_2), Block.NOTIFY_ALL);
				world.updateNeighbors(pos, Blocks.AIR);
				state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
			}
		}
	}
}
