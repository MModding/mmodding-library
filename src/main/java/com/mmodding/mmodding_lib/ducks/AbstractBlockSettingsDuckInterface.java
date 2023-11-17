package com.mmodding.mmodding_lib.ducks;

import org.quiltmc.qsl.base.api.util.TriState;

public interface AbstractBlockSettingsDuckInterface {

	TriState mmodding_lib$getTranslucent();

	boolean mmodding_lib$getInvisibleSides();

	void mmodding_lib$setTranslucent(TriState triState);

	void mmodding_lib$setInvisibleSides(boolean invisibleSides);
}
