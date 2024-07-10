package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
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
public class EntityTypeMixin<E extends Entity> implements LangContainer, InternalDataAccess.LangProcessorAccess<EntityType<E>> {

	@Unique
	public LangProcessor<EntityType<E>> langProcessor = LangProcessor.standard();

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
	public LangProcessor<EntityType<E>> langProcessor() {
		return this.langProcessor;
	}
}
