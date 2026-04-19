package com.mmodding.library.datagen.api.model.block;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;

public class MModdingTexturedModels {

	public static final TexturedModel.Provider ORIENTABLE_WITH_BACK = TexturedModel.createDefault(
		block -> TextureMapping.orientableCube(block)
			.put(TextureSlot.BACK, TextureMapping.getBlockTexture(block, "_back")),
		MModdingModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM_BACK
	);
}
