package com.mmodding.library.datagen.api.loot.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

@FunctionalInterface
public interface EntityLootProcessor {

	LootTable.Builder process(EntityType<?> entityType);
}
