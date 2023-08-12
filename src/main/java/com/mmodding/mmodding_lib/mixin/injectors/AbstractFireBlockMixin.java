package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.ShouldLightCustomPortal;
import com.mmodding.mmodding_lib.library.helpers.CustomSquaredPortalAreaHelper;
import com.mmodding.mmodding_lib.library.portals.Ignition;
import com.mmodding.mmodding_lib.library.portals.squared.AbstractSquaredPortal;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin extends BlockMixin implements ShouldLightCustomPortal {

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {

		if(!state.isOf(oldState.getBlock())) {

			for (Identifier identifier : MModdingGlobalMaps.getAllCustomSquaredPortalKeys()) {
				AbstractSquaredPortal squaredPortal = MModdingGlobalMaps.getAbstractSquaredPortal(identifier);
				Ignition.Fire fireIgnition = squaredPortal.getIgnition().toFire();

				if (fireIgnition != null) {

					if (state.isOf(fireIgnition.getFire())) {

						squaredPortal.getNewCustomPortal(world, pos, Direction.Axis.X).ifPresent(CustomSquaredPortalAreaHelper::createPortal);
					}
				}
			}
		}
	}

	@Override
	public boolean shouldLightCustomPortalAt(World world, BlockPos pos, Direction direction) {
		for (Identifier identifier : MModdingGlobalMaps.getAllCustomSquaredPortalKeys()) {
			AbstractSquaredPortal squaredPortal = MModdingGlobalMaps.getAbstractSquaredPortal(identifier);

			Ignition.Fire fireIgnition = squaredPortal.getIgnition().toFire();

			if (fireIgnition != null) {

				if (this.getDefaultState().isOf(fireIgnition.getFire())) {

					boolean bl = false;
					BlockPos.Mutable mutable = pos.mutableCopy();

					for (Direction defaultDirection : Direction.values()) {
						if (world.getBlockState(mutable.set(pos).move(defaultDirection)).isOf(squaredPortal.getFrameBlock())) {
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

						return squaredPortal.getNewCustomPortal(world, pos, axis).isPresent();
					}
				}
			}
		}

		return false;
	}
}
