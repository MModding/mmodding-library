package com.mmodding.mmodding_lib.library.integrations.modmenu;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import net.minecraft.util.Identifier;

public interface BadgeRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomBadge item && this.isNotRegistered()) {
			ModMenuIntegration.CUSTOM_BADGES_REGISTRY.put(identifier, item);
			this.setRegistered();
		}
	}
}
