package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.loot.entity.EntityLootContainer;
import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class EntityTypeMixin<E extends Entity> implements EntityLootContainer {

	@Unique
	public EntityLootProcessor<E> entityLootProcessor = null;

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Entity> EntityType<T> loot(EntityLootProcessor<T> processor) {
		this.entityLootProcessor = (EntityLootProcessor<E>) processor;
		return (EntityType<T>) (Object) this;
	}
}
