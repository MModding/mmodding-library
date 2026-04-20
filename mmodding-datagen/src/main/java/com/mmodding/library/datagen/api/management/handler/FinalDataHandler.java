package com.mmodding.library.datagen.api.management.handler;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a final type of data (to be handled as it is).
 * It is a particular case when the way of processing the data for all elements of the type.
 * @param <T> the data type
 */
public interface FinalDataHandler<T> extends DataHandler<T> {

	/**
	 * The main method of the data handler. All automated data providers with be given here.
	 * @param pack the pack
	 * @param finalContent the final contents
	 */
	void handleContent(FabricDataGenerator.Pack pack, List<T> finalContent);

	static <T, D extends DataProvider> D with(FabricDataGenerator.Pack pack, List<T> finalContent, FinalFactory<T, D> factory) {
		return pack.addProvider((FabricDataGenerator.Pack.Factory<D>) (output -> factory.create(finalContent, output)));
	}

	static <T, D extends DataProvider> D with(FabricDataGenerator.Pack pack, List<T> finalContent, RegistryDependentFinalFactory<T, D> factory) {
		return pack.addProvider((output, future) -> factory.create(finalContent, output, future));
	}

	/**
	 * @see FabricDataGenerator.Pack.Factory
	 */
	@FunctionalInterface
	interface FinalFactory<T, D extends DataProvider> {

		D create(List<T> finalContent, FabricPackOutput output);
	}

	/**
	 * @see FabricDataGenerator.Pack.RegistryDependentFactory
	 */
	@FunctionalInterface
	interface RegistryDependentFinalFactory<T, D extends DataProvider> {

		D create(List<T> finalContent, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture);
	}
}
