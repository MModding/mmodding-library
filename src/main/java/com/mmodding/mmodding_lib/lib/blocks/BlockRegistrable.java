package com.mmodding.mmodding_lib.lib.blocks;

import com.mmodding.mmodding_lib.lib.utils.Registrable;
import com.mmodding.mmodding_lib.lib.utils.RegistrationUtils;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public interface BlockRegistrable extends Registrable {
    default void register(Identifier identifier) {
        if (this instanceof CustomBlock && this.isNotRegistered()) {
            RegistrationUtils.registerBlock(identifier, (CustomBlock) this);
            this.setRegistered();
        }
    }

    @Deprecated
    default void register(Identifier identifier, BlockItem blockItem) {
        if (this instanceof Block && this.isNotRegistered()) {
            RegistrationUtils.registerNormalBlock(identifier, (Block) this, blockItem);
            this.setRegistered();
        }
    }
}
