package com.mmodding.mmodding_lib.mixin.accessors.client;

import net.minecraft.client.sound.SoundLoader;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundLoader.class)
public interface SoundLoaderAccessor {

	@Accessor("resourceManager")
	ResourceManager mmodding_lib$getResourceManager();
}
