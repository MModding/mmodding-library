package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.entities.CustomEntityType;
import net.minecraft.entity.Entity;

public interface QuiltEntityTypeBuilderDuckInterface<T extends Entity> {

    CustomEntityType<T> mmodding_lib$buildCustom();
}
