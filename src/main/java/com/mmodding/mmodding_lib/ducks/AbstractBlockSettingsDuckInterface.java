package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blocks.settings.AdvancedBlockSettings;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.fabricmc.fabric.api.util.TriState;

@InternalOf(AdvancedBlockSettings.class)
public interface AbstractBlockSettingsDuckInterface {

	float mmodding_lib$getInnerVelocityMultiplier();

	TriState mmodding_lib$getTranslucent();

	boolean mmodding_lib$getInvisibleSides();

	void mmodding_lib$setInnerVelocityMultiplier(float innerVelocityMultiplier);

	void mmodding_lib$setTranslucent(TriState triState);

	void mmodding_lib$setInvisibleSides(boolean invisibleSides);
}
