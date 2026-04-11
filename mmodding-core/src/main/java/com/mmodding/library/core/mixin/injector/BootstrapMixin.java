package com.mmodding.library.core.mixin.injector;

import com.mmodding.library.core.impl.PostContent;
import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {

	@Inject(method = "bootStrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;bootStrap()V", shift = At.Shift.AFTER))
	private static void afterInit(CallbackInfo ci) {
		PostContent.POST_CONTENT.invoker().run();
	}
}
