package com.mmodding.mmodding_lib.library.structures;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.util.Identifier;

public interface StructureRegistrable extends Registrable {

    default void register(Identifier identifier) {
        if (this instanceof CustomStructure && this.isNotRegistered()) {
            RegistrationUtils.registerStructure(identifier, (CustomStructure<?>) this);
            this.setRegistered();
       }
    }
}
