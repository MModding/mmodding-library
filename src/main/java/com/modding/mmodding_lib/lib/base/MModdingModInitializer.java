package com.modding.mmodding_lib.lib.base;

import com.modding.mmodding_lib.MModdingLib;
import com.modding.mmodding_lib.lib.initializers.ElementsInitializer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public interface MModdingModInitializer extends ModInitializer {

	List<ElementsInitializer> getElementsInitializers();

	@Override
	@OverridingMethodsMustInvokeSuper
	default void onInitialize(ModContainer mod) {
		MModdingLib.mmoddingMods.add(mod);
		for (ElementsInitializer elementsInitializer: this.getElementsInitializers()) elementsInitializer.register();
	}

}
