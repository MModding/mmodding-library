package com.mmodding.library.energy.impl.item;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.item.ItemEnergy;
import com.mmodding.library.energy.api.storage.EnergyStorage;
import com.mmodding.library.energy.impl.storage.ItemEnergyStorageImpl;
import com.mmodding.library.java.api.container.Pair;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class ItemEnergyImpl {

	public static final ItemApiLookup<EnergyStorage, ContainerItemContext> ENERGY = ItemApiLookup.get(MModdingLibrary.createId("energy"), EnergyStorage.class, ContainerItemContext.class);

	public static final DataComponentType<Long> ENERGY_AMOUNT = DataComponentType.<Long>builder()
		.persistent(Codec.LONG)
		.networkSynchronized(ByteBufCodecs.LONG)
		.build();

	private static final Set<Item> DEFINITIONS_LOCK = Sets.newIdentityHashSet();

	private static final Map<Item, Pair<Long, EnergyUnit>> DEFINITIONS = new IdentityHashMap<>();

	private static final Map<ItemEnergy.AccessKey, Item> ACCESS_KEYS = new IdentityHashMap<>();

	public static EnergyStorage accessStorage(ItemEnergy.AccessKey accessKey, ItemStack stack) {
		if (stack.getItem().equals(ACCESS_KEYS.get(accessKey))) {
			Pair<Long, EnergyUnit> definition = DEFINITIONS.get(stack.getItem());
			return new ItemEnergyStorageImpl(stack, definition.first(), definition.second());
		}
		else {
			throw new IllegalArgumentException("Invalid access for the given key!");
		}
	}

	public static ItemEnergy.AccessKey defineEnergyStorage(Item item, long capacity, EnergyUnit unit, ItemEnergy.StorageQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(item)) {
			throw new IllegalStateException("Item " + item + " already has a defined storage query!");
		}
		DEFINITIONS_LOCK.add(item);
		DEFINITIONS.put(item, Pair.create(capacity, unit));
		ItemEnergy.AccessKey key = new ItemEnergy.AccessKey();
		ACCESS_KEYS.put(key, item);
		ENERGY.registerForItems(
			(stack, context) -> handler.handle(stack, context, new ItemEnergyStorageImpl(
				stack,
				DEFINITIONS.get(item).first(),
				DEFINITIONS.get(item).second())
			), item
		);
		return key;
	}

	public static void classload() {}

	static {
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, MModdingLibrary.createId("item_energy_storages"), ItemEnergyImpl.ENERGY_AMOUNT);
	}
}
