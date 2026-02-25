package com.mmodding.library.core.api;

import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.registry.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;

public interface ExtendedModInitializer extends ModInitializer {

	void setupManager(ElementsManager manager);

	@Override
	@ApiStatus.Internal
	default void onInitialize() {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		ElementsManagerImpl manager = new ElementsManagerImpl();
		this.setupManager(manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.loadElements(advanced);
		DatagenContainerCallback.EVENT.register(containers -> {
			for (int i = 0; i < containers.size(); i++) {
				EntrypointContainer<DataGeneratorEntrypoint> container = containers.get(i);
				if (container.getProvider().getMetadata().getId().equals(advanced.getMetadata().getId())) {
					containers.set(i, DatagenContainerCallback.createDummyEntrypointContainer(
						advanced.getMetadata().getId(),
						builder -> manager.loadBootstraps(advanced, builder),
						containers.get(i).getEntrypoint()
					));
					return;
				}
			}
			containers.add(
				DatagenContainerCallback.createDummyEntrypointContainer(
					advanced.getMetadata().getId(),
					builder -> manager.loadBootstraps(advanced, builder),
					null
				)
			);
		});
		this.onInitialize(advanced);
	}

	void onInitialize(AdvancedContainer mod);
}
