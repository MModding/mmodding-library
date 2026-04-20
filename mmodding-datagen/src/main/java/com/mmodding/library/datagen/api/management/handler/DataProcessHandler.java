package com.mmodding.library.datagen.api.management.handler;

import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a type of data to be processed.
 * @param <T> the data type
 * @param <P> the data processor type
 */
public interface DataProcessHandler<T, P> extends DataHandler<T> {

	/**
	 * The main method of the data handler. All automated data providers with be given here.
	 * @param pack the pack
	 * @param contentToProcess the contents to process
	 */
	void handleContent(FabricDataGenerator.Pack pack, BiList<P, List<T>> contentToProcess);

	static <T, P, D extends DataProvider> D provider(FabricDataGenerator.Pack pack, BiList<P, List<T>> contentToProcess, ProcessFactory<T, P, D> factory) {
		return pack.addProvider((FabricDataGenerator.Pack.Factory<D>) output -> factory.create(contentToProcess, output));
	}

	static <T, P, D extends DataProvider> D provider(FabricDataGenerator.Pack pack, BiList<P, List<T>> contentToProcess, RegistryDependentProcessFactory<T, P, D> factory) {
		return pack.addProvider((output, future) -> factory.create(contentToProcess, output, future));
	}

	/**
	 * @see FabricDataGenerator.Pack.Factory
	 */
	@FunctionalInterface
	interface ProcessFactory<T, P, D extends DataProvider> {

		D create(BiList<P, List<T>> contentToProcess, FabricPackOutput output);
	}

	/**
	 * @see FabricDataGenerator.Pack.RegistryDependentFactory
	 */
	@FunctionalInterface
	interface RegistryDependentProcessFactory<T, P, D extends DataProvider> {

		D create(BiList<P, List<T>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture);
	}
}
