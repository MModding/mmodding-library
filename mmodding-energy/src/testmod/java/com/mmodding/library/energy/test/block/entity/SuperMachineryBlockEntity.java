package com.mmodding.library.energy.test.block.entity;

import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.test.init.EnergyTestBlockEntities;
import com.mmodding.library.energy.test.inventory.SuperMachineryMenu;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SuperMachineryBlockEntity extends BaseContainerBlockEntity {

	private NonNullList<ItemStack> items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);

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
		this.items = items;
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(input, this.items);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		ContainerHelper.saveAllItems(output, this.items);
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new SuperMachineryMenu(containerId, inventory, this);
	}

	@Override
	public int getContainerSize() {
		return 2;
	}

	public static void tick(Level level, BlockPos pos, BlockState blockState, SuperMachineryBlockEntity blockEntity) {
		if (level instanceof ServerLevel serverLevel) {
			EnergyStorage storage = BlockEnergy.queryStorage(serverLevel, pos, Direction.UP);
			if (storage == null) {
				throw new IllegalStateException();
			}
			if (storage.amount() >= 20 && blockEntity.getItem(0).is(Items.EMERALD)) {
				try (Transaction transaction = Transaction.openOuter()) {
					storage.revoke(transaction, 20);
					if ((blockEntity.getItem(1).is(Items.DIAMOND) || blockEntity.getItem(1).isEmpty()) && blockEntity.getItem(1).getCount() < blockEntity.getItem(1).getMaxStackSize()) {
						blockEntity.removeItem(0, 1);
						blockEntity.setItem(1, new ItemStack(Items.DIAMOND, blockEntity.getItem(1).count() + 1));
						transaction.commit();
					}
				}
			}
		}
	}
}
