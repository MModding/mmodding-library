package com.mmodding.library.block.api.properties;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.world.level.block.state.BlockBehaviour;

@InjectedContent(BlockBehaviour.Properties.class)
public interface MModdingBlockProperties {

	/**
	 * Allows setting custom block properties.
	 * @param property the custom block property
	 * @param value the value
	 * @return the current block properties
	 * @param <T> the class type of the property
	 */
	<T> BlockBehaviour.Properties custom(CustomBlockProperty<T> property, T value);
}
