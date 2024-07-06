package com.mmodding.library.datagen.mixin;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements LangContainer, LangProcessorAccess<Block> {

	@Unique
	public LangProcessor<Block> langProcessor = LangProcessor.standard();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T lang(LangProcessor<T> processor) {
		this.langProcessor = (LangProcessor<Block>) processor;
		return (T) this;
	}

	@Override
	public RegistryKey<Registry<Block>> registry() {
		return RegistryKeys.BLOCK;
	}

	@Override
	public LangProcessor<Block> processor() {
		return this.langProcessor;
	}
}
