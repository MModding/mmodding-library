package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import com.mmodding.mmodding_lib.library.utils.RenderLayerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public interface BlockRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if ((this instanceof BlockWithEntity || this instanceof Block) && this instanceof BlockWithItem blockWithItem && this.isNotRegistered()) {
			RegistrationUtils.registerBlock(identifier, blockWithItem);
			this.setRegistered();
		}
	}

	@Deprecated
	default void register(Identifier identifier, BlockItem blockItem) {
		if (this instanceof Block block && this.isNotRegistered()) {
			RegistrationUtils.registerNormalBlock(identifier, block, blockItem);
			this.setRegistered();
		}
	}

	default void cutout() {
		if (this instanceof Block block) RenderLayerUtils.setCutout(block);
	}

	default void translucent() {
		if (this instanceof Block block) RenderLayerUtils.setTranslucent(block);
	}
}
