package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.BootstrapProvider;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BootstrapFunctionImpl<T> implements RegistryBuilder.BootstrapFunction<T> {

	private final AdvancedContainer mod;
	private final BootstrapProvider<T> provider;

	public BootstrapFunctionImpl(AdvancedContainer mod, BootstrapProvider<T> provider) {
		this.mod = mod;
		this.provider = provider;
	}

	@Override
	public void run(Registerable<T> registerable) {
		this.provider.run(this.mod, registerable);
	}
}
