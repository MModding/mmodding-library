package com.mmodding.mmodding_lib.ducks;

public interface AbstractBlockSettingsDuckInterface {

	boolean getTranslucent();

	boolean getNotTranslucent();

	boolean getInvisibleSides();

	void setTranslucent(boolean translucent);

	void setNotTranslucent(boolean notTranslucent);

	void setInvisibleSides(boolean invisibleSides);
}
