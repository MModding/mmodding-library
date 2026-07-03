package com.mmodding.library.block.impl.properties;

import com.mmodding.library.block.api.properties.CustomBlockProperty;
import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.api.registry.companion.IdentityCompanion;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class CustomBlockPropertyImpl<T> implements CustomBlockProperty<T> {

	public static final IdentityCompanion<BlockBehaviour.Properties, Object> PROPERTIES_COMPANION = IdentityCompanion.create();

	private final Identifier identifier;
	private final Class<?> type;
	private final T defaultValue;

	public CustomBlockPropertyImpl(Identifier identifier, Class<?> type, T defaultValue) {
		this.identifier = identifier;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Block block, CustomBlockProperty<T> setting) {
		if (PROPERTIES_COMPANION.hasCompanion(block.properties())) {
			LiteRegistry<?> companion = PROPERTIES_COMPANION.getCompanion(block.properties());
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
		if (obj instanceof CustomBlockPropertyImpl<?> other) {
			return this.identifier.equals(other.identifier) && this.type.equals(other.type) && this.defaultValue.equals(other.defaultValue);
		}
		else {
			return super.equals(obj);
		}
	}

	@Override
	public String toString() {
		return "CustomBlockProperty[" + this.identifier.toString() + "]";
	}
}
