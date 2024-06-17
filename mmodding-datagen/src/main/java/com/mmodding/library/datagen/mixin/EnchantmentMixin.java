package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Enchantment.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class EnchantmentMixin implements LangContainer, LangProcessorAccess<Enchantment> {

	@Unique
	public LangProcessor<Enchantment> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<Enchantment>) processor;
		return (T) this;
	}

	@Override
	public Type type() {
		return Type.BLOCK;
	}

	@Override
	public LangProcessor<Enchantment> processor() {
		return this.langProcessor;
	}
}
