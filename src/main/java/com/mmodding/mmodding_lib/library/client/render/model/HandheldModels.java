package com.mmodding.mmodding_lib.library.client.render.model;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.HashSet;
import java.util.Set;

@FunctionalInterface
public interface HandheldModels {

	Set<Identifier> MODDED_HANDHELD_MODELS = new HashSet<>();

	Event<HandheldModels> EVENT = Event.create(HandheldModels.class, callbacks -> stack -> {
		for (HandheldModels callback : callbacks) {
			Identifier identifier = callback.getModelForStack(stack);
			if (identifier != null) {
				return identifier;
			}
		}
		return null;
	});

	Identifier getModelForStack(ItemStack stack);
}
