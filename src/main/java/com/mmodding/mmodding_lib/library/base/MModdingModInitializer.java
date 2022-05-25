package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public interface MModdingModInitializer extends ModInitializer {

	List<ElementsInitializer> getElementsInitializers();

	@Override
	@OverridingMethodsMustInvokeSuper
	default void onInitialize(ModContainer mod) {
		MModdingLib.mmoddingMods.add(MModdingModContainer.from(mod));
		for (ElementsInitializer elementsInitializer: this.getElementsInitializers()) elementsInitializer.register();
	}

}
