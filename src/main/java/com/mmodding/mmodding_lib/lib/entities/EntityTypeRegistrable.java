package com.mmodding.mmodding_lib.lib.entities;

import com.mmodding.mmodding_lib.lib.utils.Registrable;
import com.mmodding.mmodding_lib.lib.utils.RegistrationUtils;
import net.minecraft.util.Identifier;

public interface EntityTypeRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomEntityType<?> && this.isNotRegistered()) {
			RegistrationUtils.registerEntityType(identifier, (CustomEntityType<?>) this);
		}
	}
}
