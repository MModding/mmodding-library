package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.tag.TagProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class TagTypeImpl<T> implements DataContentType<T, TagProcessor<T>> {

	private final ResourceKey<? extends Registry<T>> registry;

	public TagTypeImpl(ResourceKey<? extends Registry<T>> registry) {
		this.registry = registry;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<TagProcessor<T>, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedTagProvider<>(this.registry, contentToProcess, output, future));
	}

	private static class AutomatedTagProvider<T> extends FabricTagProvider<T> {

		private final BiList<TagProcessor<T>, List<T>> contentToProcess;
		private final Function<T, ResourceKey<T>> reverseLookupLogic;

		@SuppressWarnings("unchecked")
		protected AutomatedTagProvider(ResourceKey<? extends Registry<T>> registry, BiList<TagProcessor<T>, List<T>> contentToProcess, FabricDataOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, registry, future);
			this.contentToProcess = contentToProcess;
			if (registry.equals(Registries.BLOCK)) {
				this.reverseLookupLogic = (Function<T, ResourceKey<T>>) (Function<?, ?>) (Function<Block, ResourceKey<Block>>) block -> block.builtInRegistryHolder().key();
			}
			else if (registry.equals(Registries.ITEM)) {
				this.reverseLookupLogic = (Function<T, ResourceKey<T>>) (Function<?, ?>) (Function<Item, ResourceKey<Item>>) item -> item.builtInRegistryHolder().key();
			}
			else if (registry.equals(Registries.FLUID)) {
				this.reverseLookupLogic = (Function<T, ResourceKey<T>>) (Function<?, ?>) (Function<Fluid, ResourceKey<Fluid>>) fluid -> fluid.builtInRegistryHolder().key();
			}
			else if (registry.equals(Registries.ENTITY_TYPE)) {
				this.reverseLookupLogic = (Function<T, ResourceKey<T>>) (Function<?, ?>) (Function<EntityType<?>, ResourceKey<EntityType<?>>>) type -> type.builtInRegistryHolder().key();
			}
			else if (registry.equals(Registries.GAME_EVENT)) {
				this.reverseLookupLogic = (Function<T, ResourceKey<T>>) (Function<?, ?>) (Function<GameEvent, ResourceKey<GameEvent>>) event -> event.builtInRegistryHolder().key();
			}
			else {
				this.reverseLookupLogic = super::reverseLookup;
			}
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.contentToProcess.forEach((processor, elements) -> {
				for (T element : elements) {
					processor.process(this::getOrCreateTagBuilder, element);
				}
			});
		}

		@Override
		protected ResourceKey<T> reverseLookup(T element) {
			return this.reverseLookupLogic.apply(element);
		}

		@Override
		public String getName() {
			return "Automated " + super.getName();
		}
	}
}
