package com.mmodding.library.datagen.api.model.block;

import net.minecraft.client.data.models.model.TexturedModel;

public class MModdingTexturedModels {

	public static final TexturedModel.Provider ORIENTABLE_WITH_BACK = TexturedModel.createDefault(MModdingTextureMappings::orientableWithBack, MModdingModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM_BACK);
}
