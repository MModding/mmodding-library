package com.mmodding.mmodding_lib.library.initializers;

import com.mmodding.mmodding_lib.library.initializers.invokepoints.BootstrapInvokePoint;

public interface BootstrapElementsInitializer {

	default BootstrapInvokePoint getInvokePoint() {
		return BootstrapInvokePoint.before(BootstrapInvokePoint.Type.FLAMMABLES);
	}

	void registerBootstrap();
}
