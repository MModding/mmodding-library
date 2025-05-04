package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.entities.CustomEntityType;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.entity.Entity;

@InternalOf( CustomEntityType.class)
public interface FabricEntityTypeBuilderDuckInterface<T extends Entity> {

    CustomEntityType<T> mmodding_lib$buildCustom();
}
