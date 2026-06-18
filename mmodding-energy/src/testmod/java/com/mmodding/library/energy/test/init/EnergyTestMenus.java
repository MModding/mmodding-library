package com.mmodding.library.energy.test.init;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.energy.test.inventory.SuperMachineryMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class EnergyTestMenus {

	public static final MenuType<SuperMachineryMenu> SUPER_MACHINERY = new MenuType<>(SuperMachineryMenu::new, FeatureFlags.DEFAULT_FLAGS);

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.MENU, "super_machinery", SUPER_MACHINERY);
	}
}
