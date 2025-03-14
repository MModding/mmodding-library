package com.mmodding.library.core.api.management.initializer;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.impl.MModdingInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.registry.RegistryBuilder;

public interface ExtendedModInitializer extends ModInitializer {

	static ElementsManager getManager(String modId) {
		return MModdingInitializer.MANAGERS.get(modId);
	}

	void setupManager(ElementsManager.Builder manager);

	@Override
	default void onInitialize() {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		ElementsManagerImpl.Builder builder = new ElementsManagerImpl.Builder();
		this.setupManager(builder);
		ElementsManagerImpl manager = builder.build();
		MModdingLibrary.getAllManagers().put(mod.getMetadata().getId(), manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.loadElements(advanced);
		DatagenContainerCallback.EVENT.register(containers -> {
			containers.add(
				DatagenContainerCallback.createDummyEntrypoint(
					new DataGeneratorEntrypoint() {

						@Override
						public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {}

						@Override
						public void buildRegistry(RegistryBuilder registryBuilder) {
							manager.loadBootstraps(advanced, registryBuilder);
						}
					},
					advanced.getMetadata().getId()
				)
			);
		});
		this.onInitialize(advanced);
	}

	void onInitialize(AdvancedContainer mod);
}
