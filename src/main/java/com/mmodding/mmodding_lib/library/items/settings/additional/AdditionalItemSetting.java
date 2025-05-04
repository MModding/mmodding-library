package com.mmodding.mmodding_lib.library.items.settings.additional;

import net.minecraft.item.Item;

import java.util.function.Supplier;

public interface AdditionalItemSetting<T> {

	static <T> AdditionalItemSetting<T> create(T defaultValue) {
		return AdditionalItemSetting.create(() -> defaultValue);
	}

	static <T> AdditionalItemSetting<T> create(Supplier<T> defaultValueFactory) {
		return new AdditionalItemSettingImpl<>(defaultValueFactory);
	}

	T get(Item item);
}
