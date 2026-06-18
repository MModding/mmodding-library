package com.mmodding.library.energy.impl.item;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.item.ItemEnergy;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.storage.ItemEnergyStorageImpl;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;

import java.util.Set;

public class ItemEnergyImpl {

	public static final ItemApiLookup<EnergyStorage, Void> ENERGY = ItemApiLookup.get(MModdingLibrary.createId("energy"), EnergyStorage.class, Void.class);

	public static final DataComponentType<Long> ENERGY_AMOUNT = DataComponentType.<Long>builder()
		.persistent(Codec.LONG)
		.networkSynchronized(ByteBufCodecs.LONG)
		.build();

	private static final Set<Item> DEFINITIONS_LOCK = Sets.newIdentityHashSet();

	public static void defineEnergyStorage(Item item, long capacity, EnergyUnit unit, ItemEnergy.StorageQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(item)) {
			throw new IllegalStateException("Item " + item + " already has a defined storage query!");
		}
		DEFINITIONS_LOCK.add(item);
		ENERGY.registerForItems(
			(stack, _) -> handler.handle(stack, new ItemEnergyStorageImpl(stack, capacity, unit)),
			item
		);
	}

	public static void classload() {}

	static {
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, MModdingLibrary.createId("item_energy_storages"), ItemEnergyImpl.ENERGY_AMOUNT);
	}
}
