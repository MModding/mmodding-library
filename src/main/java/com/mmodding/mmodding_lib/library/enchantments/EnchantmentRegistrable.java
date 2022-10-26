package com.mmodding.mmodding_lib.library.enchantments;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;

public interface EnchantmentRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof Enchantment enchantment && this.isNotRegistered()) {
			RegistrationUtils.registerEnchantment(identifier, enchantment);
		}
	}
}
