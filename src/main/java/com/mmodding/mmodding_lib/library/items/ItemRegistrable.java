package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface ItemRegistrable extends Registrable {

    default void register(Identifier identifier) {
        if (this instanceof Item item && this.isNotRegistered()) {
            RegistrationUtils.registerItem(identifier, item);
            this.setRegistered();
        }
    }
}
