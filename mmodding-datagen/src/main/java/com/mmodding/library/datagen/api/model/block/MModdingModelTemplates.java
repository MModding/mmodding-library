package com.mmodding.library.datagen.api.model.block;

import com.mmodding.library.core.api.MModdingLibrary;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.Optional;

public class MModdingModelTemplates {

	/**
	 * <code>CUBE_DIRECTIONAL</code> variant but it applies on x-axis and not z-axis
	 */
	public static final ModelTemplate CUBE_ROLL = create(
		"cube_roll", TextureSlot.UP, TextureSlot.DOWN,
		TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST
	);

	public static final ModelTemplate CUBE_ORIENTABLE_TOP_BOTTOM_BACK = create(
		"orientable_with_bottom_and_back",
		TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.FRONT, TextureSlot.BACK
	);

	private static ModelTemplate create(String path, TextureSlot... slots) {
		return new ModelTemplate(Optional.of(MModdingLibrary.createId("block/" + path)), Optional.empty(), slots);
	}
}
