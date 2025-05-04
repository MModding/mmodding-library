package com.mmodding.mmodding_lib.library.items.data;

import com.mmodding.mmodding_lib.library.network.support.type.NetworkMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface HiddenStackDataInsertionCallback {

	Event<HiddenStackDataInsertionCallback> EVENT = EventFactory.createArrayBacked(HiddenStackDataInsertionCallback.class, callbacks -> (stack, storage) -> {
		for (HiddenStackDataInsertionCallback callback : callbacks) {
			callback.insert(stack, storage);
		}
	});

	void insert(ItemStack stack, NetworkMap storage);
}
