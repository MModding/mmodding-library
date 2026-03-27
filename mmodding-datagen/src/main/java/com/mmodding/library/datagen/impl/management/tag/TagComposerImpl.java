package com.mmodding.library.datagen.impl.management.tag;

import com.mmodding.library.java.api.list.BiList;
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
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public final class TagComposerImpl<T> extends FabricTagProvider<T> {

	private final BiList<TagKey<T>, List<T>> contentToCollect;
	private final Function<T, RegistryKey<T>> reverseLookup;

	@SuppressWarnings("unchecked")
	public TagComposerImpl(RegistryKey<? extends Registry<T>> registry, BiList<TagKey<T>, List<T>> contentToCollect, FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
		super(output, registry, future);
		this.contentToCollect = contentToCollect;
		this.reverseLookup = (Function<T, RegistryKey<T>>) this.createReverseLookup();
	}

	@SuppressWarnings("unchecked")
	private Function<?, ?> createReverseLookup() {
		if (this.registryRef.equals(RegistryKeys.BLOCK)) return (Function<Block, RegistryKey<Block>>) block -> block.getRegistryEntry().registryKey();
		else if (this.registryRef.equals(RegistryKeys.ITEM)) return (Function<Item, RegistryKey<Item>>) item -> item.getRegistryEntry().registryKey();
		else if (this.registryRef.equals(RegistryKeys.FLUID)) return (Function<Fluid, RegistryKey<Fluid>>) fluid -> fluid.getRegistryEntry().registryKey();
		else if (this.registryRef.equals(RegistryKeys.ENTITY_TYPE)) return (Function<EntityType<?>, RegistryKey<EntityType<?>>>) type -> type.getRegistryEntry().registryKey();
		else if (this.registryRef.equals(RegistryKeys.GAME_EVENT)) return (Function<GameEvent, RegistryKey<GameEvent>>) event -> event.getRegistryEntry().registryKey();
		return element -> super.reverseLookup((T) element);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		this.contentToCollect.forEach(((tag, elements) -> {
			FabricTagBuilder builder = this.getOrCreateTagBuilder(tag);
			elements.forEach(builder::add);
		}));
	}

	@Override
	protected RegistryKey<T> reverseLookup(T element) {
		return this.reverseLookup.apply(element);
	}

	@Override
	public String getName() {
		return "Automated " + this.registryRef + " " + super.getName();
	}
}
