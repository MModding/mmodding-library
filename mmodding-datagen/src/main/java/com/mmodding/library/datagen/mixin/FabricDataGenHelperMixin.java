package com.mmodding.library.datagen.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.library.datagen.impl.AutomatedDataGeneratorImpl;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

// would be good if we find a better way to do that, relying on internals is bad
@SuppressWarnings("UnstableApiUsage")
@Mixin(value = FabricDataGenHelper.class, remap = false)
public class FabricDataGenHelperMixin {

	@SuppressWarnings("unchecked")
	@ModifyExpressionValue(method = "runInternal", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/api/FabricLoader;getEntrypointContainers(Ljava/lang/String;Ljava/lang/Class;)Ljava/util/List;"))
	private static <T> List<EntrypointContainer<T>> applyAutomation(List<EntrypointContainer<T>> original) {
		return ((List<EntrypointContainer<T>>) (List<?>) AutomatedDataGeneratorImpl
			.provideDataGenerators(new ArrayList<>((List<EntrypointContainer<DataGeneratorEntrypoint>>) (List<?>) original)));
	}
}
