package com.mmodding.library.energy.test.inventory;

import com.mmodding.library.energy.test.init.EnergyTestMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SuperMachineryMenu extends AbstractContainerMenu {

	private final Container container;

	public SuperMachineryMenu(int containerId, Inventory inventory) {
		super(EnergyTestMenus.SUPER_MACHINERY, containerId);
		this.container = new SimpleContainer(2);
		this.addSlot(new Slot(this.container, 0, 64, 32));
		this.addSlot(new Slot(this.container, 1, 64, 64));
		this.addStandardInventorySlots(inventory, 8, 84);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
