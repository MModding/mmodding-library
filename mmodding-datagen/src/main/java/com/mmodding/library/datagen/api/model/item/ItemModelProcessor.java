package com.mmodding.library.datagen.api.model.item;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;

public interface ItemModelProcessor {

	void process(ItemModelGenerator generator, Item item);
}
