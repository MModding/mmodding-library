package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.api.loot.entity.EntityLootContainer;
import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.impl.InternalDataAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class EntityTypeMixin<E extends Entity> implements LangContainer, EntityLootContainer, InternalDataAccess.LangProcessorAccess<EntityType<E>> {

	@Unique
	public EntityLootProcessor<E> entityLootProcessor = null;

	@Unique
	public LangProcessor<EntityType<E>> langProcessor = null;

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<EntityType<E>>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<EntityType<?>>> langRegistry() {
		return RegistryKeys.ENTITY_TYPE;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Entity> EntityType<T> loot(EntityLootProcessor<T> processor) {
		this.entityLootProcessor = (EntityLootProcessor<E>) processor;
		return (EntityType<T>) (Object) this;
	}

	@Override
	public LangProcessor<EntityType<E>> langProcessor() {
		return this.langProcessor;
	}
}
