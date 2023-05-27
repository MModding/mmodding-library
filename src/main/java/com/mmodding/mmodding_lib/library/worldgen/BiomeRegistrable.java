package com.mmodding.mmodding_lib.library.worldgen;

import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.util.Identifier;

public interface BiomeRegistrable extends Registrable {

    default void register(Identifier identifier) {
        if (this instanceof CustomBiome customBiome && this.isNotRegistered()) {
            RegistrationUtils.registerBiome(identifier, customBiome.getBiome());
            this.setRegistered();
        }
    }
}
