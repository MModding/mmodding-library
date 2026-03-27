package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.tag.TagProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class TagTypeImpl<T> implements DataContentType<T, TagProcessor<T>> {

	private final RegistryKey<? extends Registry<T>> registry;

	public TagTypeImpl(RegistryKey<? extends Registry<T>> registry) {
		this.registry = registry;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<TagProcessor<T>, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedTagProvider<>(this.registry, contentToProcess, output, future));
	}

	private static class AutomatedTagProvider<T> extends FabricTagProvider<T> {

		private final BiList<TagProcessor<T>, List<T>> contentToProcess;
		private final Function<T, RegistryKey<T>> reverseLookupLogic;

		@SuppressWarnings("unchecked")
		protected AutomatedTagProvider(RegistryKey<? extends Registry<T>> registry, BiList<TagProcessor<T>, List<T>> contentToProcess, FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
			super(output, registry, future);
			this.contentToProcess = contentToProcess;
			if (registry.equals(RegistryKeys.BLOCK)) {
				this.reverseLookupLogic = (Function<T, RegistryKey<T>>) (Function<?, ?>) (Function<Block, RegistryKey<Block>>) block -> block.getRegistryEntry().registryKey();
			}
			else if (registry.equals(RegistryKeys.ITEM)) {
				this.reverseLookupLogic = (Function<T, RegistryKey<T>>) (Function<?, ?>) (Function<Item, RegistryKey<Item>>) item -> item.getRegistryEntry().registryKey();
			}
			else if (registry.equals(RegistryKeys.FLUID)) {
				this.reverseLookupLogic = (Function<T, RegistryKey<T>>) (Function<?, ?>) (Function<Fluid, RegistryKey<Fluid>>) fluid -> fluid.getRegistryEntry().registryKey();
			}
			else if (registry.equals(RegistryKeys.ENTITY_TYPE)) {
				this.reverseLookupLogic = (Function<T, RegistryKey<T>>) (Function<?, ?>) (Function<EntityType<?>, RegistryKey<EntityType<?>>>) type -> type.getRegistryEntry().registryKey();
			}
			else if (registry.equals(RegistryKeys.GAME_EVENT)) {
				this.reverseLookupLogic = (Function<T, RegistryKey<T>>) (Function<?, ?>) (Function<GameEvent, RegistryKey<GameEvent>>) event -> event.getRegistryEntry().registryKey();
			}
			else {
				this.reverseLookupLogic = super::reverseLookup;
			}
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (T element : elements) {
					processor.process(this::getOrCreateTagBuilder, element);
				}
			});
		}

		@Override
		protected RegistryKey<T> reverseLookup(T element) {
			return this.reverseLookupLogic.apply(element);
		}

		@Override
		public String getName() {
			return "Automated " + super.getName();
		}
	}
}
