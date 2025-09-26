package com.mmodding.library.core.mixin.injector;

import com.mmodding.library.core.impl.PostContent;
import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {

	@Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registries;bootstrap()V", shift = At.Shift.AFTER))
	private static void afterInit(CallbackInfo ci) {
		PostContent.POST_CONTENT.invoker().run();
	}
}
