package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.content.ResourceProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstapContext;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BootstrapFunctionImpl<T> implements RegistrySetBuilder.RegistryBootstrap<T> {

	private final AdvancedContainer mod;
	private final ResourceProvider<T> provider;

	public BootstrapFunctionImpl(AdvancedContainer mod, ResourceProvider<T> provider) {
		this.mod = mod;
		this.provider = provider;
	}

	@Override
	public void run(BootstapContext<T> registerable) {
		this.provider.run(this.mod, registerable);
	}
}
