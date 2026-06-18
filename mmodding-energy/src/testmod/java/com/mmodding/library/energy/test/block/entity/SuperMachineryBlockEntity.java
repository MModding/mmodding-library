package com.mmodding.library.energy.test.block.entity;

import com.mmodding.library.energy.test.init.EnergyTestBlockEntities;
import com.mmodding.library.energy.test.inventory.SuperMachineryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SuperMachineryBlockEntity extends BaseContainerBlockEntity {

	private final NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

	public SuperMachineryBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(EnergyTestBlockEntities.SUPER_MACHINERY, worldPosition, blockState);
	}

	@Override
	protected Component getDefaultName() {
		return Component.literal("Super Machinery");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items.clear();
		this.items.addAll(items);
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new SuperMachineryMenu(containerId, inventory);
	}

	@Override
	public int getContainerSize() {
		return 2;
	}
}
