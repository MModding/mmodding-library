package com.mmodding.mmodding_lib.mixin.injectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaHelper.class)
public class AreaHelperMixin {

	@Shadow
	private @Nullable BlockPos lowerCorner;

	@Shadow
	private int height;

	@Shadow
	@Final
	private Direction.Axis axis;

	@Shadow
	@Final
	private Direction negativeDir;

	@Shadow
	@Final
	private int width;

	@Shadow
	@Final
	private WorldAccess world;

	@Inject(method = "createPortal", at = @At("HEAD"))
	private void createPortal(CallbackInfo ci) {
		BlockState blockState = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
		assert this.lowerCorner != null;
		BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
			.forEach(pos -> this.world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
	}
}
