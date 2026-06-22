package com.mmodding.library.energy.test.client;

import com.mmodding.library.energy.test.client.screen.SuperMachineryScreen;
import com.mmodding.library.energy.test.init.EnergyTestMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class EnergyClientTests implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MenuScreens.register(EnergyTestMenus.SUPER_MACHINERY, SuperMachineryScreen::new);
	}
}
