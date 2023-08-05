package com.mmodding.mmodding_lib.library.blockentities;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;

public interface BlockEntityTypeRegistrable<T extends BlockEntity> extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomBlockEntityType<T> blockEntityType && this.isNotRegistered()) {
			RegistrationUtils.registerBlockEntityType(identifier, blockEntityType);
		}
	}
}
