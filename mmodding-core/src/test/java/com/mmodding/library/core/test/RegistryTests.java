package com.mmodding.library.core.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import com.mmodding.library.core.api.registry.companion.RegistryKeyAttachment;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

public class RegistryTests implements DefaultContentHolder {

	public static final RegistryCompanion<Item, Block> ITEM_COMPANION = RegistryCompanion.create(RegistryKeyAttachment.classic(Registries.ITEM));

	public RegistryTests() {
		RegistryTests.ITEM_COMPANION.getOrCreateCompanion(Items.ACACIA_BOAT);
	}

	@Override
	public void register(AdvancedContainer mod) {
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("a"), Blocks.BELL);
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("b"), Blocks.DIRT);
	}
}
