package com.mmodding.library.datagen.api.provider;

import com.mmodding.library.datagen.api.tag.ValueTagAppender;
import com.mmodding.library.datagen.impl.tag.ValueTagAppenderImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.BlockItemTagAppender;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A variant of {@link FabricTagsProvider} for builtin registries.
 * <br>It allows, in the case of builtin registries (where it is more convenient)
 * to generate the resource directly from the registry objects.
 * @param <T> the element type
 * @see FabricTagsProvider
 */
public abstract class BuiltinRegistryTagsProvider<T> extends FabricTagsProvider<T> {

	private final Function<T, ResourceKey<T>> keyFinder;

	public BuiltinRegistryTagsProvider(FabricPackOutput output, Registry<T> registry, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registry.key(), registryLookupFuture);
		this.keyFinder = e -> registry.getResourceKey(e).orElseThrow(() -> new IllegalArgumentException("Unregistered entry: " + e));
	}

	protected ValueTagAppender<T> valueBuilder(TagKey<T> tag) {
		return new ValueTagAppenderImpl<>(this.keyFinder, this.builder(tag));
	}

	/**
	 * @see FabricTagsProvider.BlockTagsProvider
	 */
	public abstract static class BlockTagsProvider extends BuiltinRegistryTagsProvider<Block> {

		public BlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
			super(output, BuiltInRegistries.BLOCK, registryLookupFuture);
		}

		protected BlockItemTagAppender<Block> builder(TagKey<Block> tag) {
			return new BlockItemTagAppender<>(super.builder(tag)) {

				@Override
				protected ResourceKey<Block> convertElement(BlockItemId element) {
					return element.block();
				}
			};
		}
	}

	/**
	 * @see FabricTagsProvider.BlockEntityTypeTagsProvider
	 */
	public abstract static class BlockEntityTypeTagsProvider extends BuiltinRegistryTagsProvider<BlockEntityType<?>> {

		public BlockEntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
			super(output, BuiltInRegistries.BLOCK_ENTITY_TYPE, registryLookupFuture);
		}
	}

	/**
	 * @see FabricTagsProvider.ItemTagsProvider
	 */
	public abstract static class ItemTagsProvider extends BuiltinRegistryTagsProvider<Item> {

		@Nullable
		private final Function<TagKey<Block>, TagBuilder> blockTagBuilderProvider;

		public ItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture, BuiltinRegistryTagsProvider.@Nullable BlockTagsProvider blockTagsProvider) {
			super(output, BuiltInRegistries.ITEM, registryLookupFuture);
			this.blockTagBuilderProvider = blockTagsProvider == null ? null : blockTagsProvider::getOrCreateRawBuilder;
		}

		public ItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
			this(output, registryLookupFuture, null);
		}

		public void copy(BlockItemTagId blockItemTag) {
			this.copy(blockItemTag.block(), blockItemTag.item());
		}

		public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
			TagBuilder blockTagBuilder = Objects.requireNonNull(this.blockTagBuilderProvider, "Pass Block tags provider via constructor to use copy").apply(blockTag);
			TagBuilder itemTagBuilder = this.getOrCreateRawBuilder(itemTag);
			blockTagBuilder.build().forEach(itemTagBuilder::add);
		}

		protected BlockItemTagAppender<Item> builder(TagKey<Item> tag) {
			return new BlockItemTagAppender<>(super.builder(tag)) {

				@Override
				protected ResourceKey<Item> convertElement(BlockItemId element) {
					return element.item();
				}
			};
		}
	}

	/**
	 * @see FabricTagsProvider.FluidTagsProvider
	 */
	public abstract static class FluidTagsProvider extends BuiltinRegistryTagsProvider<Fluid> {

		public FluidTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
			super(output, BuiltInRegistries.FLUID, registryLookupFuture);
		}
	}

	/**
	 * @see FabricTagsProvider.EntityTypeTagsProvider
	 */
	public abstract static class EntityTypeTagsProvider extends BuiltinRegistryTagsProvider<EntityType<?>> {

		public EntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
			super(output, BuiltInRegistries.ENTITY_TYPE, registryLookupFuture);
		}
	}
}
