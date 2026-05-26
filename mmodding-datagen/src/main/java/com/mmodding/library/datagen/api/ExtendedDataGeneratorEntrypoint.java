package com.mmodding.library.datagen.api;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.impl.management.RegistryDataManagerImpl;
import com.mmodding.library.datagen.impl.management.TaskDataManagerImpl;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import org.jetbrains.annotations.ApiStatus;

/**
 * An extension of {@link DataGeneratorEntrypoint} which helps with world registry configurations
 * and provides automations for resource generations.
 */
public interface ExtendedDataGeneratorEntrypoint extends DataGeneratorEntrypoint {

	@Override
	@ApiStatus.Internal
	default void buildRegistry(RegistrySetBuilder registryBuilder) {
		RegistryDataManagerImpl manager = new RegistryDataManagerImpl();
		this.setupManager(manager);
		AdvancedContainer mod = AdvancedContainer.of(MModdingLibrary.getModContainer(this.getClass()));
		manager.provideBootstraps(mod, registryBuilder);
	}

	@Override
	default void onInitializeDataGenerator(FabricDataGenerator generator) {
		TaskDataManagerImpl manager = new TaskDataManagerImpl();
		this.setupManager(manager);
		AdvancedContainer mod = AdvancedContainer.of(MModdingLibrary.getModContainer(this.getClass()));
		FabricDataGenerator.Pack pack = generator.createPack();
		manager.loadElements(pack);
		pack.addProvider((output, future) -> new TaskDataManagerImpl.WorldRegistriesExporter(manager, output, future));
		this.onInitializeDataGenerator(mod, generator, pack);
	}

	void setupManager(DataManager manager);

	void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack);
}
