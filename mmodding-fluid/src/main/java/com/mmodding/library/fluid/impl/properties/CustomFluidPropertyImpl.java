package com.mmodding.library.fluid.impl.properties;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.IdentityCompanion;
import com.mmodding.library.fluid.api.properties.CustomFluidProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;

public class CustomFluidPropertyImpl<T> implements CustomFluidProperty<T> {

	public static final IdentityCompanion<Fluid, Object> PROPERTIES_COMPANION = IdentityCompanion.create();

	private final Identifier identifier;
	private final Class<?> type;
	private final T defaultValue;

	public CustomFluidPropertyImpl(Identifier identifier, Class<?> type, T defaultValue) {
		this.identifier = identifier;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Fluid fluid, CustomFluidProperty<T> setting) {
		if (PROPERTIES_COMPANION.hasCompanion(fluid)) {
			LiteRegistry<?> companion = PROPERTIES_COMPANION.getCompanion(fluid);
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
	public Class<?> getType() {
		return this.type;
	}

	@Override
	public T getDefault() {
		return this.defaultValue;
	}

	@Override
	public int hashCode() {
		return this.identifier.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomFluidPropertyImpl<?> other) {
			return this.identifier.equals(other.identifier) && this.type.equals(other.type) && this.defaultValue.equals(other.defaultValue);
		}
		else {
			return super.equals(obj);
		}
	}

	@Override
	public String toString() {
		return "CustomFluidProperty[" + this.identifier.toString() + "]";
	}
}
