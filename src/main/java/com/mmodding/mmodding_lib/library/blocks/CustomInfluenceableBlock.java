package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.utils.ObjectUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public abstract class CustomInfluenceableBlock<E extends Enum<E> & StringIdentifiable> extends CustomBlock {

	private E defaultValue = null;

	protected EnumProperty<E> createInfluenceProperty(Class<E> type, E defaultValue) {
		this.defaultValue = defaultValue;
		return EnumProperty.of("influence", type);
	}

	protected abstract EnumProperty<E> getInfluenceProperty();

    public CustomInfluenceableBlock(Settings settings) {
		this(settings, false);
    }

    public CustomInfluenceableBlock(Settings settings, boolean hasItem) {
        this(settings, hasItem, (ItemGroup) null);
    }

	public CustomInfluenceableBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomInfluenceableBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(settings, hasItem, itemSettings);
	    this.setDefaultState(this.getDefaultState().with(
			this.getInfluenceProperty(),
		    ObjectUtils.assumeNotNull(this.defaultValue, () -> new IllegalStateException("Influence Property Default Value Not Defined"))
	    ));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.UP ? state.with(this.getInfluenceProperty(), this.getInfluence(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(this.getInfluenceProperty(), this.getInfluence(ctx.getWorld().getBlockState(ctx.getBlockPos().up())));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(this.getInfluenceProperty());
	}

	public E getInfluence(BlockState state) {
		return state.get(this.getInfluenceProperty());
	}
}
