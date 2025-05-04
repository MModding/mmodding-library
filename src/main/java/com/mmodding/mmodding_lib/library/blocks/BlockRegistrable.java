package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import com.mmodding.mmodding_lib.library.client.render.layer.RenderLayerOperations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
			RegistrationUtils.registerBlockAndItem(identifier, block, blockItem);
			this.setRegistered();
		}
	}

	@Environment(EnvType.CLIENT)
	default void cutout() {
		if (this instanceof Block block) RenderLayerOperations.setCutout(block);
	}

	@Environment(EnvType.CLIENT)
	default void translucent() {
		if (this instanceof Block block) RenderLayerOperations.setTranslucent(block);
	}

	@Environment(EnvType.CLIENT)
	default void transparent() {
		if (this instanceof Block block) RenderLayerOperations.setTransparent(block);
	}
}
