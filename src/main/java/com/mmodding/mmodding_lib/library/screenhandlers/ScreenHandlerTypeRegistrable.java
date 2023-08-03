package com.mmodding.mmodding_lib.library.screenhandlers;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public interface ScreenHandlerTypeRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof ScreenHandlerType<?> screenHandlerType && this.isNotRegistered()) {
			RegistrationUtils.registerScreenHandlerType(identifier, screenHandlerType);
			this.setRegistered();
		}
	}
}
