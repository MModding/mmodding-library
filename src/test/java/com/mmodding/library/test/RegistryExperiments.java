package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.initializer.ExtendedModInitializer;
import com.mmodding.library.registry.api.ElementsManager;
import com.mmodding.library.registry.api.companion.RegistryCompanion;
import com.mmodding.library.registry.api.content.SimpleContentHolder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;

public class RegistryExperiments implements SimpleContentHolder<Item> {

	public final RegistryCompanion<Item, Block> companion;

	public RegistryExperiments(Registry<Item> registry, AdvancedContainer mod) {
		this.companion = RegistryCompanion.create(registry, mod.createId("item_blocks"));
		this.companion.addCompanion(Items.ACACIA_BOAT);
	}

	@Override
	public void register(Registry<Item> registry, AdvancedContainer mod) {
		this.companion.getCompanion(Items.ACACIA_BOAT).register(mod.createId("a"), Blocks.BELL);
		this.companion.getCompanion(Items.ACACIA_BOAT).register(mod.createId("b"), Blocks.DIRT);
	}

	public static ElementsManager getManager() {
		return ExtendedModInitializer.getManager("rea_experiments");
	}

	public static RegistryExperiments getInstance() {
		return RegistryExperiments.getManager().fetchClass(RegistryExperiments.class);
	}
}
