package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.impl.Registrable;
import com.mmodding.library.registry.content.DefaultContentHolder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;

public class MModdingTestBlocks implements DefaultContentHolder {

	public static final Registrable<Block> FIRST_BLOCK = (Registrable<Block>) new Block(null);

	public static final Registrable<Block> SECOND_BLOCK = (Registrable<Block>) new Block(null);

	@Override
	public void register(AdvancedContainer mod) {
		mod.withRegistry(Registries.BLOCK).execute(linked -> {
			FIRST_BLOCK.register(linked.createId("first_block"));
			SECOND_BLOCK.register(linked.createId("second_block"));
		});
	}
}
