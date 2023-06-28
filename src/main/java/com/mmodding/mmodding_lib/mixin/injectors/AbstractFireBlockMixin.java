package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalAreaHelper;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		if(!state.isOf(oldState.getBlock())) {
			for (Identifier identifier : MModdingGlobalMaps.getCustomSquaredPortalKeys()) {
				Pair<? extends Block, ? extends CustomSquaredPortalBlock> pair = MModdingGlobalMaps.getCustomSquaredPortal(identifier);
				if (pair.getRight().shouldLightLikeVanilla()) {
					Optional<CustomSquaredPortalAreaHelper> optional = CustomSquaredPortalAreaHelper.getNewCustomPortal(
						pair.getLeft(), pair.getRight(), world, pos, Direction.Axis.X
					);
					optional.ifPresent(CustomSquaredPortalAreaHelper::createPortal);
				}
			}
		}
	}

	@Inject(method = "shouldLightPortalAt", at = @At("HEAD"), cancellable = true)
	private static void shouldLightPortal(World world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		for (Identifier identifier : MModdingGlobalMaps.getCustomSquaredPortalKeys()) {
			Pair<? extends  Block, ? extends CustomSquaredPortalBlock> pair = MModdingGlobalMaps.getCustomSquaredPortal(identifier);

			if (pair.getRight().shouldLightLikeVanilla()) {
				boolean bl = false;
				BlockPos.Mutable mutable = pos.mutableCopy();

				for (Direction defaultDirection : Direction.values()) {
					if (world.getBlockState(mutable.set(pos).move(defaultDirection)).isOf(pair.getLeft())) {
						bl = true;
						break;
					}
				}

				if (bl) {
					Direction.Axis axis;
					if (direction.getAxis().isHorizontal()) {
						axis = direction.rotateYCounterclockwise().getAxis();
					} else {
						axis = Direction.Type.HORIZONTAL.randomAxis(world.random);
					}
					cir.setReturnValue(CustomSquaredPortalAreaHelper.getNewCustomPortal(pair.getLeft(), pair.getRight(), world, pos, axis).isPresent());
				}
			}
		}
	}
}
