package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;
import net.minecraft.registry.Registerable;

public interface BootstrapProvider<T> {

	void run(AdvancedContainer mod, Registerable<T> registerable);
}
