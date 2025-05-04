package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.network.support.type.NetworkMap;
import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.item.ItemStack;

@ClassExtension(ItemStack.class)
public interface HiddenDataStorage {

	default NetworkMap getHiddenDataStorage() {
		throw new AssertionError();
	}

	default void setHiddenDataStorage(NetworkMap storage) {
		throw new AssertionError();
	}
}
