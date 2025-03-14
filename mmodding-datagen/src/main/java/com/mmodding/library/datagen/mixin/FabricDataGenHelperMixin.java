package com.mmodding.library.datagen.mixin;

import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
import com.mmodding.library.datagen.impl.AutomatedDataGeneratorImpl;
import com.mmodding.library.datagen.impl.lang.TranslationSupportImpl;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// would be good if we find a better way to do that, relying on internals is bad
@SuppressWarnings("UnstableApiUsage")
@Mixin(value = FabricDataGenHelper.class, remap = false)
public class FabricDataGenHelperMixin {

	@Inject(method = "runInternal", at = @At("HEAD"))
	private static void preSetup(CallbackInfo ci) {
		TranslationSupportImpl.defaultTranslationSupports();
		DatagenContainerCallback.EVENT.register(AutomatedDataGeneratorImpl::provideDataGenerators);
	}
}
