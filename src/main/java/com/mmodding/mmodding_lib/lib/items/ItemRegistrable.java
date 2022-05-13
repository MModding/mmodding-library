package com.mmodding.mmodding_lib.lib.items;

import com.mmodding.mmodding_lib.lib.utils.Registrable;
import com.mmodding.mmodding_lib.lib.utils.RegistrationUtils;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface ItemRegistrable extends Registrable {
    default void register(Identifier identifier) {
        if (this instanceof Item && this.isNotRegistered()) {
            RegistrationUtils.registerItem(identifier, (Item) this);
            this.setRegistered();
        }
    }
}
