package com.mmodding.library.energy.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.energy.test.init.EnergyTestBlockEntities;
import com.mmodding.library.energy.test.init.EnergyTestBlocks;
import com.mmodding.library.energy.test.init.EnergyTestMenus;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class EnergyTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(EnergyTestBlocks::register);
		manager.content(EnergyTestMenus::register);
		manager.content(EnergyTestBlockEntities::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static String namespace() {
		return "mmodding_energy_testmod";
	}

	public static Identifier createId(String path) {
		return Identifier.fromNamespaceAndPath(namespace(), path);
	}

	public static <T> ResourceKey<T> createKey(ResourceKey<? extends Registry<T>> registry, String path) {
		return ResourceKey.create(registry, createId(path));
	}
}
