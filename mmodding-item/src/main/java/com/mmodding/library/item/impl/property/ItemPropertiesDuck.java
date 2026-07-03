package com.mmodding.library.item.impl.property;

import com.mmodding.library.core.api.management.info.DuckInterface;
import net.minecraft.world.item.Item;

@DuckInterface(Item.class)
public interface ItemPropertiesDuck {

	void mmodding$applyCustomProperties(Item item);
}
