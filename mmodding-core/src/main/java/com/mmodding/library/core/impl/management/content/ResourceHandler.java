package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ResourceHandler extends FabricDynamicRegistryProvider {

	private final Consumer<Entries> consumer;

	public ResourceHandler(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, Consumer<Entries> consumer) {
		super(output, registriesFuture);
		this.consumer = consumer;
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		this.consumer.accept(entries);
	}

	@Override
	public String getName() {
		return "Internal Resource Handler";
	}
}
