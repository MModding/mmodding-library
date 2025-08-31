package com.mmodding.library.datagen.api.loot.entity;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

@InjectedContent(EntityType.class)
public interface EntityLootContainer {

	default <T extends Entity> EntityType<T> loot(EntityLootProcessor<T> processor) {
		throw new IllegalStateException();
	}
}
