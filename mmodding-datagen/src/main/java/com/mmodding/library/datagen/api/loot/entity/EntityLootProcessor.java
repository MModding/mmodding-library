package com.mmodding.library.datagen.api.loot.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTable;

@FunctionalInterface
public interface EntityLootProcessor<T extends Entity> {

	LootTable.Builder process(EntityType<T> entityType);
}
