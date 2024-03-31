package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingRegistries;
import com.mmodding.library.registry.api.companion.RegistryCompanion;
import com.mmodding.library.registry.api.companion.RegistryKeyAttachment;
import com.mmodding.library.registry.api.content.DefaultContentHolder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

public class RegistryTests implements DefaultContentHolder {

	public static final RegistryCompanion<Item, Block> ITEM_COMPANION = RegistryCompanion.create(RegistryKeyAttachment.classic(Registries.ITEM));

	public RegistryTests() {
		RegistryTests.ITEM_COMPANION.getOrCreateCompanion(Items.ACACIA_BOAT);
	}

	@Override
	public void register(AdvancedContainer mod) {
		MModdingRegistries.DIFFERED_SEED.put(World.NETHER, true);
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("a"), Blocks.BELL);
		RegistryTests.ITEM_COMPANION.getCompanion(Items.ACACIA_BOAT).register(mod.createId("b"), Blocks.DIRT);
	}
}
