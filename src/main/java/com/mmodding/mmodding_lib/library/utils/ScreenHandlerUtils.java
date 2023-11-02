package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.ScreenHandlerAccessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ScreenHandlerUtils {

	public static void createPlayerSlots(ScreenHandler screenHandler, PlayerInventory playerInventory) {
		ScreenHandlerUtils.createPlayerInventory(screenHandler, playerInventory);
		ScreenHandlerUtils.createPlayerHotBar(screenHandler, playerInventory);
	}

	public static void createPlayerInventory(ScreenHandler screenHandler, PlayerInventory playerInventory) {
		ScreenHandlerAccessor accessor = (ScreenHandlerAccessor) screenHandler;

		for (int line = 0; line < 3; line++) {
			for (int column = 0; column < 9; column++) {
				accessor.invokeAddSlot(new Slot(playerInventory, column + line * 9 + 9, 8 + column * 18, 84 + line * 18));
			}
		}
	}

	public static void createPlayerHotBar(ScreenHandler screenHandler, PlayerInventory playerInventory) {
		ScreenHandlerAccessor accessor = (ScreenHandlerAccessor) screenHandler;

		for (int hotBarSlot = 0; hotBarSlot < 9; hotBarSlot++) {
			accessor.invokeAddSlot(new Slot(playerInventory, hotBarSlot, 8 + hotBarSlot * 18, 142));
		}
	}
}
