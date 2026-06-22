package com.mmodding.library.energy.test.client.screen;

import com.mmodding.library.energy.test.inventory.SuperMachineryMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SuperMachineryScreen extends AbstractContainerScreen<SuperMachineryMenu> {

	public SuperMachineryScreen(SuperMachineryMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	public void init() {
		super.init();
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}
}
