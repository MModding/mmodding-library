
package com.mmodding.mmodding_lib.library.items.settings.additional;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.Supplier;

public class AdditionalItemSettingImpl<T> implements AdditionalItemSetting<T> {

	@ApiStatus.Internal
	public static final Map<Item.Settings, Map<AdditionalItemSettingImpl<Object>, Object>> ADDITIONAl_SETTINGS = new WeakHashMap<>();

	private final Supplier<T> defaultValueFactory;
	private final Map<Item, T> values;

	public AdditionalItemSettingImpl(Supplier<T> defaultValueFactory) {
		this.defaultValueFactory = defaultValueFactory;
		this.values = new Object2ObjectOpenHashMap<>();
	}

	public T get(Item item) {
		return this.values.containsKey(item) ? this.values.get(item) : this.defaultValueFactory.get();
	}

	@SuppressWarnings("unchecked")
	public void append(Item.Settings settings, T value) {
		AdditionalItemSettingImpl.ADDITIONAl_SETTINGS
			.computeIfAbsent(settings, ignored -> new WeakHashMap<>())
			.put((AdditionalItemSettingImpl<Object>) this, value);
	}

	public static void applyToItem(Item.Settings settings, Item item) {
		for (Map.Entry<AdditionalItemSettingImpl<Object>, Object> entry : AdditionalItemSettingImpl.ADDITIONAl_SETTINGS.get(settings).entrySet()) {
			entry.getKey().values.put(item, entry.getValue());
		}
	}
}
