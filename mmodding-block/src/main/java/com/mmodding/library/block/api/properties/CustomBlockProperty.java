package com.mmodding.library.block.api.properties;

import com.mmodding.library.block.impl.properties.CustomBlockPropertyImpl;
import com.mmodding.library.core.api.MModdingLibrary;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * @apiNote Supports {@link Block.Properties#ofLegacyCopy(BlockBehaviour)} && {@link Block.Properties#ofFullCopy(BlockBehaviour)}.
 */
public interface CustomBlockProperty<T> {

	/**
	 * Creates the {@link CustomBlockProperty} instance for these parameters.
	 */
	static <T> CustomBlockProperty<T> of(Identifier identifier, Class<T> type, T defaultValue) {
		return new CustomBlockPropertyImpl<>(identifier, type, defaultValue);
	}

	/**
	 * Gets the value of a {@link CustomBlockProperty}.
	 * @param property the custom block property
	 * @return the associated value
	 */
	static <T> T get(Block block, CustomBlockProperty<T> property) {
		return CustomBlockPropertyImpl.get(block, property);
	}

	/**
	 * The identifier of the {@link CustomBlockProperty}.
	 * If you believe that the property you make is conventional, consider using the <code>mmodding</code> namespace (see {@link MModdingLibrary#createId(String)}).
	 * @return the identifier
	 */
	Identifier getIdentifier();

	/**
	 * The class object of the value type.
	 * @return the class object
	 */
	Class<T> getType();

	/**
	 * Returns the default value of this setting for a block not specifying it.
	 * @return the value
	 */
	T getDefault();
}
