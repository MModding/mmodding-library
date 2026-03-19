package com.mmodding.library.datagen.mixin;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.nio.file.Path;

@Mixin(value = FabricLanguageProvider.class, remap = false)
public interface FabricLanguageProviderAccessor {

	@Accessor("languageCode")
	String mmodding$getLanguageCode();

	@Invoker("getLangFilePath")
	Path mmodding$getLangFilePath(String code);
}
