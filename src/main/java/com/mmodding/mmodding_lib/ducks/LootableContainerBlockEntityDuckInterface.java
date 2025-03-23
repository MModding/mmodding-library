package com.mmodding.mmodding_lib.ducks;

import net.minecraft.item.ItemStack;

public interface LootableContainerBlockEntityDuckInterface {

	void mmodding_lib$addPredeterminedLoot(ItemStack predeterminedLoots);

	void mmodding_lib$clearPredeterminedLoot();
}
