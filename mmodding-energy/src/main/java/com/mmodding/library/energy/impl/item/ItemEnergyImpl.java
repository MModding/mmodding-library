package com.mmodding.library.energy.impl.item;

import com.google.common.collect.Sets;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.item.ItemEnergy;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.impl.EnergyComponentImpl;
import com.mmodding.library.energy.impl.data.ItemEnergyData;
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
import java.util.function.Function;

public class ItemEnergyImpl {

	public static final ItemApiLookup<EnergyAccess, ContainerItemContext> ENERGY = ItemApiLookup.get(MModdingLibrary.createId("energy"), EnergyAccess.class, ContainerItemContext.class);

	public static final DataComponentType<Long> ENERGY_AMOUNT = DataComponentType.<Long>builder()
		.persistent(Codec.LONG)
		.networkSynchronized(ByteBufCodecs.LONG)
		.build();

	private static final Set<Item> DEFINITIONS_LOCK = Sets.newIdentityHashSet();

	private static final Map<Item, Pair<Function<ItemStack, Long>, EnergyUnit>> DEFINITIONS = new IdentityHashMap<>();

	public static EnergyComponent retrieveFrom(ItemStack stack) {
		Pair<Function<ItemStack, Long>, EnergyUnit> definition = DEFINITIONS.get(stack.getItem());
		return new EnergyComponentImpl(() -> definition.first().apply(stack), definition.second(), new ItemEnergyData(stack));
	}

	public static void defineEnergy(Item item, Function<ItemStack, Long> capacityGetter, EnergyUnit unit, ItemEnergy.AccessQueryHandler handler) {
		if (DEFINITIONS_LOCK.contains(item)) {
			throw new IllegalStateException("Item " + item + " already has a defined query handler!");
		}
		DEFINITIONS_LOCK.add(item);
		DEFINITIONS.put(item, Pair.create(capacityGetter, unit));
		ENERGY.registerForItems(
			(stack, context) -> handler.handle(stack, context, new EnergyComponentImpl(() -> capacityGetter.apply(stack), unit, new ItemEnergyData(stack))), item
		);
	}

	public static void classload() {}

	static {
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, MModdingLibrary.createId("item_energy_storages"), ItemEnergyImpl.ENERGY_AMOUNT);
	}
}
