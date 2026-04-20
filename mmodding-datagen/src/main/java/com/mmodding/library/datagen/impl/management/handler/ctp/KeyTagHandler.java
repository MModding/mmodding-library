package com.mmodding.library.datagen.impl.management.handler.ctp;

import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.tag.KeyTagProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public class KeyTagHandler<T> implements DataProcessHandler<T, KeyTagProcessor<T>> {

	private final ResourceKey<? extends Registry<T>> registry;
	private final Class<T> type;

	@Override
	public Class<T> getType() {
		return this.type;
	}

	public KeyTagHandler(ResourceKey<? extends Registry<T>> registry, Class<T> type) {
		this.registry = registry;
		this.type = type;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<KeyTagProcessor<T>, List<T>> contentToProcess) {
		DataProcessHandler.provider(pack, contentToProcess, (c, o, f) -> new AutomatedTagProvider<>(this.registry, c, o, f));
	}

	private static class AutomatedTagProvider<T> extends FabricTagsProvider<T> {

		private final BiList<KeyTagProcessor<T>, List<T>> contentToProcess;

		protected AutomatedTagProvider(ResourceKey<? extends Registry<T>> registry, BiList<KeyTagProcessor<T>, List<T>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, registry, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (T element : elements) {
					processor.process(this::builder, element);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated ResourceKey " + this.registryKey + " " + super.getName();
		}
	}
}
