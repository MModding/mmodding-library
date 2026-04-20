package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.block.api.catalog.DoubleCropBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Function;

@Mixin(PitcherCropBlock.class)
public class PitcherCropBlockMixin {

	@WrapMethod(method = "makeShapes")
	public Function<BlockState, VoxelShape> cancelIfCustom(Operation<Function<BlockState, VoxelShape>> original) {
		return (((Object) this) instanceof DoubleCropBlock) ? _ -> Shapes.empty() : original.call();
	}
}
