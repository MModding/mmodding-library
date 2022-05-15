package com.mmodding.mmodding_lib.lib.entities;

import com.mmodding.mmodding_lib.lib.utils.Registrable;
import com.mmodding.mmodding_lib.lib.utils.RegistrationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public interface EntityTypeRegistrable<T extends Entity> extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomEntityType<T> && this.isNotRegistered()) {
			RegistrationUtils.registerEntityType(identifier, (CustomEntityType<T>) this);
		}
	}
}
