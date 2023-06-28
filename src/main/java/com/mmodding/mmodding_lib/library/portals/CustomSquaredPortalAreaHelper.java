package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.mixin.accessors.AreaHelperAccessor;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class CustomSquaredPortalAreaHelper extends AreaHelper {

	private final Block frameBlock;
	private final CustomSquaredPortalBlock portalBlock;

	public static Optional<CustomSquaredPortalAreaHelper> getNewCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, WorldAccess world, BlockPos pos, Direction.Axis axis) {
		return CustomSquaredPortalAreaHelper.getCustomOrEmpty(frameBlock, portalBlock, world, pos, areaHelper -> areaHelper.isValid() && ((AreaHelperAccessor) areaHelper).getFoundPortalBlocks() == 0, axis);
	}

	public static Optional<CustomSquaredPortalAreaHelper> getCustomOrEmpty(Block frameBlock, CustomSquaredPortalBlock portalBlock, WorldAccess world, BlockPos pos, Predicate<CustomSquaredPortalAreaHelper> predicate, Direction.Axis axis) {
		Optional<CustomSquaredPortalAreaHelper> optional = Optional.of(new CustomSquaredPortalAreaHelper(frameBlock, portalBlock, world, pos, axis)).filter(predicate);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis2 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
			return Optional.of(new CustomSquaredPortalAreaHelper(frameBlock, portalBlock, world, pos, axis2)).filter(predicate);
		}
	}

	public CustomSquaredPortalAreaHelper(Block frameBlock, CustomSquaredPortalBlock portalBlock, WorldAccess world, BlockPos pos, Direction.Axis axis) {
		super(world, pos, axis);
		this.frameBlock = frameBlock;
		this.portalBlock = portalBlock;
		this.accessor().setLowerCorner(this.getLowerCorner(pos));
		if (this.accessor().getLowerCorner() == null) {
			this.accessor().setLowerCorner(pos);
			this.accessor().setWidth(1);
			this.accessor().setHeight(1);
		} else {
			this.accessor().setWidth(this.accessor().invokeGetWidth());
			if (this.accessor().getWidth() > 0) {
				this.accessor().setHeight(this.accessor().invokeGetHeight());
			}
		}
	}

	public AreaHelperAccessor accessor() {
		return (AreaHelperAccessor) this;
	}

	public boolean isFrame(BlockState blockstate) {
		return blockstate.isOf(this.frameBlock);
	}

	@Override
	public void createPortal() {
		BlockState portalBlockState = this.portalBlock.getDefaultState().with(NetherPortalBlock.AXIS, this.accessor().getAxis());
		BlockPos.iterate(
			this.accessor().getLowerCorner(), this.accessor().getLowerCorner()
				.offset(Direction.UP, this.accessor().getHeight() - 1)
				.offset(this.accessor().getNegativeDir(), this.accessor().getWidth() - 1)
		).forEach(pos -> this.accessor().getWorld().setBlockState(pos, portalBlockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
	}

	public boolean validStateInsideCustomPortal(BlockState state) {
		return state.isAir() || state.isIn(BlockTags.FIRE) || state.isOf(this.portalBlock);
	}

	@Nullable
	@Override
	protected BlockPos getLowerCorner(BlockPos pos) {

		int i = Math.max(this.accessor().getWorld().getBottomY(), pos.getY() - 21);

		while(pos.getY() > i && this.validStateInsideCustomPortal(this.accessor().getWorld().getBlockState(pos.down()))) {
			pos = pos.down();
		}

		Direction direction = this.accessor().getNegativeDir().getOpposite();
		int j = this.getWidth(pos, direction) - 1;
		return j < 0 ? null : pos.offset(direction, j);
	}

	@Override
	public int getWidth(BlockPos pos, Direction direction) {

		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(int i = 0; i <= 21; ++i) {
			mutable.set(pos).move(direction, i);
			BlockState blockState = this.accessor().getWorld().getBlockState(mutable);
			if (!this.validStateInsideCustomPortal(blockState)) {
				if (this.isFrame(blockState)) {
					return i;
				}
				break;
			}

			BlockState downBlockState = this.accessor().getWorld().getBlockState(mutable.move(Direction.DOWN));
			if (!this.isFrame(downBlockState)) {
				break;
			}
		}

		return 0;
	}

	@Override
	protected boolean m_beqllhzk(BlockPos.Mutable pos, int i) {

		for(int j = 0; j < this.accessor().getWidth(); ++j) {
			BlockPos.Mutable mutable = pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), j);
			if (!this.isFrame(this.accessor().getWorld().getBlockState(mutable))) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected int m_fqjhrxgm(BlockPos.Mutable pos) {

		for(int i = 0; i < 21; ++i) {
			pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), -1);
			if (!this.isFrame(this.accessor().getWorld().getBlockState(pos))) {
				return i;
			}

			pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), this.accessor().getWidth());
			if (!this.isFrame(this.accessor().getWorld().getBlockState(pos))) {
				return i;
			}

			for(int j = 0; j < this.accessor().getWidth(); ++j) {
				pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), j);
				BlockState blockState = this.accessor().getWorld().getBlockState(pos);
				if (!this.validStateInsideCustomPortal(blockState)) {
					return i;
				}

				if (blockState.isOf(this.portalBlock)) {
					this.accessor().setFoundPortalBlocks(this.accessor().getFoundPortalBlocks() + 1);
				}
			}
		}

		return 21;
	}
}
