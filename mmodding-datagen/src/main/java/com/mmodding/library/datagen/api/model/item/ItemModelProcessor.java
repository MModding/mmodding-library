package com.mmodding.library.datagen.api.model.item;

import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.item.Item;

public interface ItemModelProcessor {

	void process(ItemModelGenerators generator, Item item);
}
