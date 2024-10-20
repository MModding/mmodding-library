package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.math.OrientedBlockPos;
import com.mmodding.mmodding_lib.library.utils.Opposable;
import com.mmodding.mmodding_lib.library.utils.TweakFunction;
import com.mmodding.mmodding_lib.mixin.accessors.AbstractBlockAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomDoubleWidthBlock extends Block implements BlockRegistrable, BlockWithItem {

	@ApiStatus.Internal
	public static final EnumProperty<DoubleWidthPart> PART = EnumProperty.of("part", DoubleWidthPart.class);

	@ApiStatus.Internal
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private BlockItem item = null;

    public CustomDoubleWidthBlock(Settings settings) {
        this(settings, false);
    }

    public CustomDoubleWidthBlock(Settings settings, boolean hasItem) {
        this(settings, hasItem, (ItemGroup) null);
    }

	public CustomDoubleWidthBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomDoubleWidthBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
        super(settings);
		this.setDefaultState(this.getDefaultState().with(CustomDoubleWidthBlock.PART, DoubleWidthPart.ORIGIN).with(FACING, Direction.NORTH));
        if (hasItem) this.item = new BlockItem(this, itemSettings);
    }

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		OrientedBlockPos oriented = OrientedBlockPos.of(ctx.getBlockPos()).apply(ctx.getPlayerFacing());
		boolean validOrigin = this.canPlacePartAt(ctx.getWorld(), new BlockPos(oriented), ctx.getWorld().getBlockState(oriented), ctx);
		boolean validSub0 = this.canPlacePartAt(ctx.getWorld(), new BlockPos(oriented.front()), ctx.getWorld().getBlockState(oriented.front()), ctx);
		boolean validSub1 = this.canPlacePartAt(ctx.getWorld(), new BlockPos(oriented.front().left()), ctx.getWorld().getBlockState(oriented.front().left()), ctx);
		boolean validSub2 = this.canPlacePartAt(ctx.getWorld(), new BlockPos(oriented.left()), ctx.getWorld().getBlockState(oriented.left()), ctx);
		return validOrigin && validSub0 && validSub1 && validSub2 ? this.getDefaultState().with(PART, DoubleWidthPart.ORIGIN).with(FACING, ctx.getPlayerFacing()) : null;
	}

	@Override
	public final boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		OrientedBlockPos origin = state.get(PART).toOrigin(pos, state.get(FACING));
		BlockPos originPos = new BlockPos(origin);
		BlockPos sub0Pos = new BlockPos(origin.front());
		BlockPos sub1Pos = new BlockPos(origin.front().left());
		BlockPos sub2Pos = new BlockPos(origin.left());
		boolean validOrigin = this.canPlacePartAt(world, originPos, world.getBlockState(originPos), null);
		boolean validSub0 = this.canPlacePartAt(world, sub0Pos, world.getBlockState(sub0Pos), null);
		boolean validSub1 = this.canPlacePartAt(world, sub1Pos, world.getBlockState(sub1Pos), null);
		boolean validSub2 = this.canPlacePartAt(world, sub2Pos, world.getBlockState(sub2Pos), null);
		return validOrigin && validSub0 && validSub1 && validSub2;
	}

	public boolean canPlacePartAt(WorldView world, BlockPos pos, BlockState state, @Nullable ItemPlacementContext ctx) {
		if (ctx != null) {
			return state.canReplace(ctx);
		}
		else {
			return ((AbstractBlockAccessor) state.getBlock()).getMaterial().isReplaceable();
		}
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		if (!world.isClient()) {
			OrientedBlockPos oriented = OrientedBlockPos.of(pos).apply(state.get(FACING));
			world.setBlockState(oriented.front(), state.with(PART, DoubleWidthPart.SUB_PART_0), Block.NOTIFY_ALL);
			world.setBlockState(oriented.front().left(), state.with(PART, DoubleWidthPart.SUB_PART_1), Block.NOTIFY_ALL);
			world.setBlockState(oriented.left(), state.with(PART, DoubleWidthPart.SUB_PART_2), Block.NOTIFY_ALL);
			world.updateNeighbors(pos, Blocks.AIR);
			state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);
		if (!world.isClient()) {
			OrientedBlockPos origin = state.get(PART).toOrigin(pos, state.get(FACING));
			world.setBlockState(origin, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			world.setBlockState(origin.front(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			world.setBlockState(origin.front().left(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			world.setBlockState(origin.left(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
			world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
		}
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.DESTROY;
	}

	@Override
	@SuppressWarnings("UnnecessaryLocalVariable")
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		AbstractBlock.AbstractBlockState s = state; // also works with State<O, S> but for some cursed reasons I just cannot use it with BlockState???
		return s.reverseCycle(FACING).with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return mirror == BlockMirror.NONE ? state : state.with(PART, state.get(PART).getOpposite()).rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(PART);
		builder.add(FACING);
	}

	@Override
    public BlockItem getItem() {
        return this.item;
    }

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }

	public enum DoubleWidthPart implements Opposable<DoubleWidthPart>, StringIdentifiable {
		ORIGIN((oriented) -> oriented),
		SUB_PART_0(OrientedBlockPos::behind),
		SUB_PART_1((oriented) -> oriented.behind().right()),
		SUB_PART_2(OrientedBlockPos::right);

		private final TweakFunction<OrientedBlockPos> tweak;

		DoubleWidthPart(TweakFunction<OrientedBlockPos> tweak) {
			this.tweak = tweak;
		}

		public OrientedBlockPos toOrigin(BlockPos pos, Direction direction) {
			return this.tweak.apply(OrientedBlockPos.of(pos).apply(direction));
		}

		@Override
		public DoubleWidthPart getOpposite() {
			return switch (this) {
				case ORIGIN -> SUB_PART_1;
				case SUB_PART_0 -> SUB_PART_2;
				case SUB_PART_1 -> ORIGIN;
				case SUB_PART_2 -> SUB_PART_0;
			};
		}

		@Override
		public String asString() {
			return switch (this) {
				case ORIGIN -> "origin";
				case SUB_PART_0 -> "sub_part_0";
				case SUB_PART_1 -> "sub_part_1";
				case SUB_PART_2 -> "sub_part_2";
			};
		}
	}
}
