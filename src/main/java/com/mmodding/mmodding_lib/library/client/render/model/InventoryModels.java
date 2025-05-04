package com.mmodding.mmodding_lib.library.client.render.model;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

@FunctionalInterface
public interface InventoryModels {

	Set<Identifier> REGISTRY = new HashSet<>();

	Event<InventoryModels> EVENT = EventFactory.createArrayBacked(InventoryModels.class, callbacks -> stack -> {
		for (InventoryModels callback : callbacks) {
			Identifier identifier = callback.getModelForStack(stack);
			if (identifier != null) {
				return identifier;
			}
		}
		return null;
	});

	Identifier getModelForStack(ItemStack stack);
}
