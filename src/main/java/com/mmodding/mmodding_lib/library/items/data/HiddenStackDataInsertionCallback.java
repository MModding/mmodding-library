package com.mmodding.mmodding_lib.library.items.data;

import com.mmodding.mmodding_lib.library.network.support.type.NetworkMap;
import net.minecraft.item.ItemStack;
import org.quiltmc.qsl.base.api.event.Event;

@FunctionalInterface
public interface HiddenStackDataInsertionCallback {

	Event<HiddenStackDataInsertionCallback> EVENT = Event.create(HiddenStackDataInsertionCallback.class, callbacks -> (stack, storage) -> {
		for (HiddenStackDataInsertionCallback callback : callbacks) {
			callback.insert(stack, storage);
		}
	});

	void insert(ItemStack stack, NetworkMap storage);
}
