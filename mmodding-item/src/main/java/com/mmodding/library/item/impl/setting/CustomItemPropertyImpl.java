package com.mmodding.library.item.impl.setting;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import com.mmodding.library.item.api.properties.CustomItemProperty;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class CustomItemPropertyImpl<T> implements CustomItemProperty<T> {

	public static final RegistryCompanion<Item, Object> REGISTRY = RegistryCompanion.create(BuiltInRegistries.ITEM);

	private final Identifier identifier;
	private final Class<T> type;
	private final T defaultValue;

	public CustomItemPropertyImpl(Identifier identifier, Class<T> type, T defaultValue) {
		this.identifier = identifier;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(ItemLike item, CustomItemProperty<T> setting) {
		if (REGISTRY.hasCompanion(item.asItem())) {
			LiteRegistry<?> companion = REGISTRY.getCompanion(item.asItem());
			if (companion.contains(setting.getIdentifier())) {
				return (T) companion.get(setting.getIdentifier());
			}
		}
		return setting.getDefault();
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public Class<T> getType() {
		return this.type;
	}

	@Override
	public T getDefault() {
		return this.defaultValue;
	}
}
