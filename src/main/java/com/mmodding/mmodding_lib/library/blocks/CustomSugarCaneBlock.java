package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomSugarCaneBlock extends SugarCaneBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private BlockItem item = null;

	private final Predicate<BlockState> validFloor;
	private final BiPredicate<BlockState, FluidState> validFluid;

    public CustomSugarCaneBlock(@Nullable Predicate<BlockState> validFloor, @Nullable BiPredicate<BlockState, FluidState> validFluid, Settings settings) {
        this(validFloor, validFluid, settings, false);
    }

    public CustomSugarCaneBlock(@Nullable Predicate<BlockState> validFloor, @Nullable BiPredicate<BlockState, FluidState> validFluid, Settings settings, boolean hasItem) {
        this(validFloor, validFluid, settings, hasItem, (ItemGroup) null);
    }

	public CustomSugarCaneBlock(@Nullable Predicate<BlockState> validFloor, @Nullable BiPredicate<BlockState, FluidState> validFluid, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(validFloor, validFluid, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomSugarCaneBlock(@Nullable Predicate<BlockState> validFloor, @Nullable BiPredicate<BlockState, FluidState> validFluid, Settings settings, boolean hasItem, Item.Settings itemSettings) {
        super(settings);
        if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.validFloor = validFloor;
		this.validFluid = validFluid;
    }

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState floorState = world.getBlockState(pos.down());

		if (floorState.isOf(this)) {
			return true;
		}

		if (this.validFloor != null ? this.validFloor.test(floorState) : (
			floorState.isIn(BlockTags.DIRT) || floorState.isOf(Blocks.SAND) || floorState.isOf(Blocks.RED_SAND)
		)) {
			for(Direction direction : Direction.Type.HORIZONTAL) {
				BlockState blockState = world.getBlockState(pos.down().offset(direction));
				FluidState fluidState = world.getFluidState(pos.down().offset(direction));

				if (this.validFluid != null ? this.validFluid.test(blockState, fluidState) : (
					fluidState.isIn(FluidTags.WATER) || blockState.isOf(Blocks.FROSTED_ICE)
				)) {
					return true;
				}
			}
		}

		return false;
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
