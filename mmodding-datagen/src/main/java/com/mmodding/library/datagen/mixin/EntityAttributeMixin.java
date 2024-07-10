package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.InternalDataAccess;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityAttribute.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class EntityAttributeMixin implements LangContainer, InternalDataAccess.LangProcessorAccess<EntityAttribute> {

	@Unique
	public LangProcessor<EntityAttribute> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<EntityAttribute>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<EntityAttribute>> langRegistry() {
		return RegistryKeys.ENTITY_ATTRIBUTE;
	}

	@Override
	public LangProcessor<EntityAttribute> langProcessor() {
		return this.langProcessor;
	}
}
