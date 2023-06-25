package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.portals.CustomPortalAreaHelper;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSquaredPortalBlock extends NetherPortalBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);
    private BlockItem item = null;

	private final Block frameBlock;

    public CustomSquaredPortalBlock(Block frameBlock, Settings settings) {
        this(frameBlock, settings, false);
    }

    public CustomSquaredPortalBlock(Block frameBlock, Settings settings, boolean hasItem) {
        this(frameBlock, settings, hasItem, (ItemGroup) null);
    }

	public CustomSquaredPortalBlock(Block frameBlock, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(frameBlock, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomSquaredPortalBlock(Block frameBlock, Settings settings, boolean hasItem, Item.Settings itemSettings) {
        super(settings);
        if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.frameBlock = frameBlock;
    }

	public void registerPortal(Identifier identifier) {
		RegistrationUtils.registerCustomPortal(identifier, this.frameBlock, this);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		Direction.Axis directionAxis = direction.getAxis();
		Direction.Axis stateAxis = state.get(AXIS);
		boolean bl = stateAxis != directionAxis && directionAxis.isHorizontal();
		return !bl && !neighborState.isOf(this) && !new CustomPortalAreaHelper(this.frameBlock, this, world, pos, stateAxis).wasAlreadyValid()
			? Blocks.AIR.getDefaultState()
			: super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
}
