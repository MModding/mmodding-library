package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.library.initializers.BootstrapElementsInitializer;
import com.mmodding.mmodding_lib.library.initializers.invokepoints.BootstrapInvokePoint;
import java.util.List;
import java.util.function.Predicate;

public interface MModdingBootstrapInitializer {

	String ENTRYPOINT_KEY = "mmodding_bootstrap_init";

	List<BootstrapElementsInitializer> getBootstrapElementsInitializers();

	default void processElementsInitializers(BootstrapInvokePoint.Placement placement, BootstrapInvokePoint.Type type) {
		Predicate<BootstrapElementsInitializer> filter = initializer -> placement.equals(initializer.getInvokePoint().getPlacement()) && type.equals(initializer.getInvokePoint().getType());
		this.getBootstrapElementsInitializers().stream().filter(filter).forEachOrdered(BootstrapElementsInitializer::registerBootstrap);
	}

	void onInitializeBootstrap(AdvancedModContainer mod);
}
