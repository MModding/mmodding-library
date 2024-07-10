package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.InternalDataAccess;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Enchantment.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class EnchantmentMixin implements LangContainer, InternalDataAccess.LangProcessorAccess<Enchantment> {

	@Unique
	public LangProcessor<Enchantment> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<Enchantment>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<Enchantment>> langRegistry() {
		return RegistryKeys.ENCHANTMENT;
	}

	@Override
	public LangProcessor<Enchantment> langProcessor() {
		return this.langProcessor;
	}
}
