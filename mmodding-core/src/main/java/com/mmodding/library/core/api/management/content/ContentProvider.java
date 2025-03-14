package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;

@FunctionalInterface
public interface ContentProvider {

	void register(AdvancedContainer mod);
}
