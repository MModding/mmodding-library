package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.blocks.CustomSquaredPortalBlock;
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

public class CustomPortalAreaHelper extends AreaHelper {

	private final Block frameBlock;
	private final CustomSquaredPortalBlock portalBlock;
	private final AbstractBlock.ContextPredicate framePredicate;

	public static Optional<CustomPortalAreaHelper> getNewCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, WorldAccess world, BlockPos pos, Direction.Axis axis) {
		return CustomPortalAreaHelper.getCustomOrEmpty(frameBlock, portalBlock, world, pos, areaHelper -> areaHelper.isValid() && ((AreaHelperAccessor) areaHelper).getFoundPortalBlocks() == 0, axis);
	}

	public static Optional<CustomPortalAreaHelper> getCustomOrEmpty(Block frameBlock, CustomSquaredPortalBlock portalBlock, WorldAccess world, BlockPos pos, Predicate<AreaHelper> predicate, Direction.Axis axis) {
		Optional<CustomPortalAreaHelper> optional = Optional.of(new CustomPortalAreaHelper(frameBlock, portalBlock, world, pos, axis)).filter(predicate);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis2 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
			return Optional.of(new CustomPortalAreaHelper(frameBlock, portalBlock, world, pos, axis2)).filter(predicate);
		}
	}

	public CustomPortalAreaHelper(Block frameBlock, CustomSquaredPortalBlock portalBlock, WorldAccess world, BlockPos pos, Direction.Axis axis) {
		super(world, pos, axis);
		this.frameBlock = frameBlock;
		this.portalBlock = portalBlock;
		this.framePredicate = (predicatedState, predicatedWorld, predicatedPos) -> predicatedState.isOf(this.frameBlock);
	}

	public AreaHelperAccessor accessor() {
		return (AreaHelperAccessor) this;
	}

	@Override
	public void createPortal() {
		BlockState blockState = this.portalBlock.getDefaultState().with(NetherPortalBlock.AXIS, this.accessor().getAxis());
		BlockPos.iterate(
			this.accessor().getLowerCorner(), this.accessor().getLowerCorner()
				.offset(Direction.UP, this.accessor().getHeight() - 1)
				.offset(this.accessor().getNegativeDir(), this.accessor().getWidth() - 1)
		).forEach(pos -> this.accessor().getWorld().setBlockState(pos, blockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
	}

	public static boolean validStateInsideCustomPortal(BlockState state, CustomSquaredPortalBlock portalBlock) {
		return state.isAir() || state.isIn(BlockTags.FIRE) || state.isOf(portalBlock);
	}

	@Nullable
	@Override
	protected BlockPos getLowerCorner(BlockPos pos) {

		int i = Math.max(this.accessor().getWorld().getBottomY(), pos.getY() - 21);

		while(pos.getY() > i && validStateInsideCustomPortal(this.accessor().getWorld().getBlockState(pos.down()), this.portalBlock)) {
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
			if (!validStateInsideCustomPortal(blockState, this.portalBlock)) {
				if (this.framePredicate.test(blockState, this.accessor().getWorld(), mutable)) {
					return i;
				}
				break;
			}

			BlockState blockState2 = this.accessor().getWorld().getBlockState(mutable.move(Direction.DOWN));
			if (!this.framePredicate.test(blockState2, this.accessor().getWorld(), mutable)) {
				break;
			}
		}

		return 0;
	}

	@Override
	protected boolean m_beqllhzk(BlockPos.Mutable pos, int i) {

		for(int j = 0; j < this.accessor().getWidth(); ++j) {
			BlockPos.Mutable mutable = pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), j);
			if (!this.framePredicate.test(this.accessor().getWorld().getBlockState(mutable), this.accessor().getWorld(), mutable)) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected int m_fqjhrxgm(BlockPos.Mutable pos) {

		for(int i = 0; i < 21; ++i) {
			pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), -1);
			if (!this.framePredicate.test(this.accessor().getWorld().getBlockState(pos), this.accessor().getWorld(), pos)) {
				return i;
			}

			pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), this.accessor().getWidth());
			if (!this.framePredicate.test(this.accessor().getWorld().getBlockState(pos), this.accessor().getWorld(), pos)) {
				return i;
			}

			for(int j = 0; j < this.accessor().getWidth(); ++j) {
				pos.set(this.accessor().getLowerCorner()).move(Direction.UP, i).move(this.accessor().getNegativeDir(), j);
				BlockState blockState = this.accessor().getWorld().getBlockState(pos);
				if (!validStateInsideCustomPortal(blockState, this.portalBlock)) {
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
