package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class ItemMixin implements LangContainer, LangProcessorAccess<Item> {

	@Unique
	public LangProcessor<Item> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<Item>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<Item>> registry() {
		return RegistryKeys.ITEM;
	}

	@Override
	public LangProcessor<Item> processor() {
		return this.langProcessor;
	}
}
