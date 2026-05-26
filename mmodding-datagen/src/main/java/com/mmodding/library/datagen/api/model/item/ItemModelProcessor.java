package com.mmodding.library.datagen.api.model.item;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.world.item.Item;

@FunctionalInterface
public interface ItemModelProcessor {

	void process(ItemModelGenerators generator, Item item);
}
