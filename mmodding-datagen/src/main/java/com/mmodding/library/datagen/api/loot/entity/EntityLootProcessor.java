package com.mmodding.library.datagen.api.loot.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTable;

@FunctionalInterface
public interface EntityLootProcessor {

	LootTable.Builder process(EntityType<?> entityType);
}
