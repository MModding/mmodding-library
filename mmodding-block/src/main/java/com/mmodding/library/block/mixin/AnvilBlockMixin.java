package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.block.api.catalog.AdvancedAnvilBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {

	@WrapMethod(method = "damage")
	private static BlockState provideNextAnvil(BlockState blockState, Operation<BlockState> original) {
		if (blockState.getBlock() instanceof AdvancedAnvilBlock anvil) {
			AdvancedAnvilBlock next = anvil.getNextAnvil();
			if (next != null) {
				return next.defaultBlockState().setValue(
					BlockStateProperties.HORIZONTAL_FACING,
					blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)
				);
			}
			else {
				return null;
			}
		}
		else {
			return original.call(blockState);
		}
	}
}
