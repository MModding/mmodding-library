package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import net.minecraft.entity.attribute.EntityAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityAttribute.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class EntityAttributeMixin implements LangContainer, LangProcessorAccess<EntityAttribute> {

	@Unique
	public LangProcessor<EntityAttribute> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<EntityAttribute>) processor;
		return (T) this;
	}

	@Override
	public Type type() {
		return Type.BLOCK;
	}

	@Override
	public LangProcessor<EntityAttribute> processor() {
		return this.langProcessor;
	}
}
