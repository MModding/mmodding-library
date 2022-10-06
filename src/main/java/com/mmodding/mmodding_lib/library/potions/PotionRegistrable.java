package com.mmodding.mmodding_lib.library.potions;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;

public interface PotionRegistrable extends Registrable {

	default void register(String string) {
		this.register(new Identifier(string));
	}

	default void register(Identifier identifier) {
		if (this instanceof Potion potion && this.isNotRegistered()) {
			RegistrationUtils.registerPotion(identifier, potion);
		}
	}
}
