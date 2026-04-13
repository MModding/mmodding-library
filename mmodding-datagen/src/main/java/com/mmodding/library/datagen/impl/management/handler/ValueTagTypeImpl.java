package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.tag.ValueTagProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ValueTagTypeImpl<T> implements DataContentType<T, ValueTagProcessor<T>> {

	private final SupportedElement supportedElement;

	public ValueTagTypeImpl(SupportedElement supportedElement) {
		this.supportedElement = supportedElement;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handleContent(FabricDataGenerator.Pack pack, BiList<ValueTagProcessor<T>, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> switch (this.supportedElement) {
			case BLOCK -> new AutomatedBlockTagsProvider((BiList<ValueTagProcessor<Block>, List<Block>>) (BiList<?, ?>) contentToProcess, output, future);
			case ITEM -> new AutomatedItemTagsProvider((BiList<ValueTagProcessor<Item>, List<Item>>) (BiList<?, ?>) contentToProcess, output, future);
			case FLUID -> new AutomatedFluidTagsProvider((BiList<ValueTagProcessor<Fluid>, List<Fluid>>) (BiList<?, ?>) contentToProcess, output, future);
			case ENTITY_TYPE -> new AutomatedEntityTypeTagsProvider((BiList<ValueTagProcessor<EntityType<?>>, List<EntityType<?>>>) (BiList<?, ?>) contentToProcess, output, future);
		});
	}

	private static class AutomatedBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

		private final BiList<ValueTagProcessor<Block>, List<Block>> contentToProcess;

		public AutomatedBlockTagsProvider(BiList<ValueTagProcessor<Block>, List<Block>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (Block block : elements) {
					processor.process(this::valueLookupBuilder, block);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + SupportedElement.BLOCK.registry + " " + super.getName();
		}
	}

	private static class AutomatedItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

		private final BiList<ValueTagProcessor<Item>, List<Item>> contentToProcess;

		public AutomatedItemTagsProvider(BiList<ValueTagProcessor<Item>, List<Item>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (Item item : elements) {
					processor.process(this::valueLookupBuilder, item);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + SupportedElement.ITEM.registry + " " + super.getName();
		}
	}

	private static class AutomatedFluidTagsProvider extends FabricTagsProvider.FluidTagsProvider {

		private final BiList<ValueTagProcessor<Fluid>, List<Fluid>> contentToProcess;

		public AutomatedFluidTagsProvider(BiList<ValueTagProcessor<Fluid>, List<Fluid>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (Fluid fluid : elements) {
					processor.process(this::valueLookupBuilder, fluid);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + SupportedElement.FLUID.registry + " " + super.getName();
		}
	}

	private static class AutomatedEntityTypeTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {

		private final BiList<ValueTagProcessor<EntityType<?>>, List<EntityType<?>>> contentToProcess;

		public AutomatedEntityTypeTagsProvider(BiList<ValueTagProcessor<EntityType<?>>, List<EntityType<?>>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (EntityType<?> type : elements) {
					processor.process(this::valueLookupBuilder, type);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + SupportedElement.ENTITY_TYPE.registry + " " + super.getName();
		}
	}

	public enum SupportedElement {

		BLOCK(Registries.BLOCK),
		ITEM(Registries.ITEM),
		FLUID(Registries.FLUID),
		ENTITY_TYPE(Registries.ENTITY_TYPE);

		private final ResourceKey<? extends Registry<?>> registry;

		SupportedElement(ResourceKey<? extends Registry<?>> registry) {
			this.registry = registry;
		}
	}
}
