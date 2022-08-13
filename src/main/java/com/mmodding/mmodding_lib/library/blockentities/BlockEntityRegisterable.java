package com.mmodding.mmodding_lib.library.blockentities;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;

public interface BlockEntityRegisterable extends Registrable {
	default void register(Identifier identifier) {
		if ((this instanceof BlockEntity blockEntity) && this.isNotRegistered()) {
			RegistrationUtils.registerBlockEntity(identifier, blockEntity);
			this.setRegistered();
		}
	}
}
