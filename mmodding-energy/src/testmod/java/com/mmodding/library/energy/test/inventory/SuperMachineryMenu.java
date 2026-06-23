package com.mmodding.library.energy.test.inventory;

import com.mmodding.library.energy.test.init.EnergyTestMenus;
import com.mmodding.library.inventory.api.menu.BasicInventoryAccessContainerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SuperMachineryMenu extends BasicInventoryAccessContainerMenu {

	public SuperMachineryMenu(int containerId, Inventory inventory, Container container) {
		super(EnergyTestMenus.SUPER_MACHINERY, 2, containerId, inventory, container);
		this.addSlot(new Slot(this.container, 0, 64, 16));
		this.addSlot(new Slot(this.container, 1, 64, 48));
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		return ItemStack.EMPTY;
	}
}
