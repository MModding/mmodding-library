package com.mmodding.library.core.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RegistryTests {

	public static final RegistryCompanion<Item, Block> ITEM_COMPANION = RegistryCompanion.create(BuiltInRegistries.ITEM);

	public RegistryTests() {
		RegistryTests.ITEM_COMPANION.getOrCreateCompanion(Items.ACACIA_BOAT);
	}

	public static void register(AdvancedContainer mod) {
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("a"), Blocks.BELL);
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("b"), Blocks.DIRT);
	}
}
