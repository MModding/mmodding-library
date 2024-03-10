package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.registry.api.Registrable;
import com.mmodding.library.registry.api.content.DefaultContentHolder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;

public class MModdingTestBlocks implements DefaultContentHolder {

	public static final Registrable<Block> FIRST_BLOCK = (Registrable<Block>) new Block(null);

	public static final Registrable<Block> SECOND_BLOCK = (Registrable<Block>) new Block(null);

	@Override
	public void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.BLOCK).execute(init -> {
			FIRST_BLOCK.register(init.createId("first_block"));
			SECOND_BLOCK.register(init.createId("second_block"));
		});
	}
}
