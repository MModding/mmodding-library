package com.mmodding.library.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = FabricDataGenHelper.class, remap = false)
public class FabricDataGenHelperMixin {

	@SuppressWarnings("unchecked")
	@ModifyExpressionValue(method = "runInternal", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/api/FabricLoader;getEntrypointContainers(Ljava/lang/String;Ljava/lang/Class;)Ljava/util/List;"))
	private static <T> List<EntrypointContainer<T>> applyAutomation(List<EntrypointContainer<T>> original) {
		DatagenContainerCallback.EVENT.invoker().modifyContainers((List<EntrypointContainer<DataGeneratorEntrypoint>>) (List<?>) original);
		return original;
	}
}
