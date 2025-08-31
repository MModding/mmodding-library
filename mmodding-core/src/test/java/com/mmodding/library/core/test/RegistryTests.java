package com.mmodding.library.core.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.registry.extension.RegistryCompanion;
import com.mmodding.library.core.api.registry.extension.RegistryKeyAttachment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

public class RegistryTests {

	public static final RegistryCompanion<Item, Block> ITEM_COMPANION = RegistryCompanion.create(RegistryKeyAttachment.classic(Registries.ITEM));

	public RegistryTests() {
		RegistryTests.ITEM_COMPANION.getOrCreateCompanion(Items.ACACIA_BOAT);
	}

	public static void register(AdvancedContainer mod) {
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("a"), Blocks.BELL);
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("b"), Blocks.DIRT);
	}
}
