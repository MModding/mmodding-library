package com.mmodding.library.datagen.api;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.impl.management.DataManagerImpl;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.ModContainer;

public interface ExtendedDataGeneratorEntrypoint extends DataGeneratorEntrypoint {

	void setupManager(DataManager manager);

	@Override
	default void onInitializeDataGenerator(FabricDataGenerator generator) {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		DataManagerImpl manager = new DataManagerImpl();
		this.setupManager(manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		FabricDataGenerator.Pack pack = generator.createPack();
		manager.loadTags(pack);
		manager.loadElements(pack);
		this.onInitializeDataGenerator(advanced, generator, pack);
	}

	void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack);
}
