package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.stat.StatType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StatType.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class StatTypeMixin<S> implements LangContainer, LangProcessorAccess<StatType<S>> {

	@Unique
	public LangProcessor<StatType<S>> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<StatType<S>>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<Item>> registry() {
		return RegistryKeys.ITEM;
	}

	@Override
	public LangProcessor<StatType<S>> processor() {
		return this.langProcessor;
	}
}
