package com.mmodding.library.inventory.api.menu;

import com.mmodding.library.java.api.annotation.DefinedByInheritors;
import com.mmodding.library.java.api.function.TriFunction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public abstract class BasicInventoryAccessContainerMenu extends AbstractContainerMenu {

	protected final Container container;

	public static <T extends BasicInventoryAccessContainerMenu> MenuType.MenuSupplier<T> menuSupplier(TriFunction<Integer, Inventory, Container, T> factory, int containerSize) {
		return (containerId, inventory) -> factory.apply(containerId, inventory, new SimpleContainer(containerSize));
	}

	protected BasicInventoryAccessContainerMenu(
		@DefinedByInheritors MenuType<?> menuType,
		@DefinedByInheritors int expectedSize,
		int containerId,
		Inventory inventory,
		Container container
	) {
		this(menuType, expectedSize, 8, 84, containerId, inventory, container);
	}

	protected BasicInventoryAccessContainerMenu(
		@DefinedByInheritors MenuType<?> menuType,
		@DefinedByInheritors int expectedSize,
		@DefinedByInheritors int inventoryLeftAnchor,
		@DefinedByInheritors int inventoryTopAnchor,
		int containerId,
		Inventory inventory,
		Container container
	) {
		super(menuType, containerId);
		this.container = container;
		checkContainerSize(this.container, expectedSize);
		this.container.startOpen(inventory.player);
		this.addStandardInventorySlots(inventory, inventoryLeftAnchor, inventoryTopAnchor);
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}
