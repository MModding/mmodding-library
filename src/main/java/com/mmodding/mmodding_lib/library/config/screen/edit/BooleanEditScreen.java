package com.mmodding.mmodding_lib.library.config.screen.edit;

import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.screen.ConfigElementsListEntry;
import com.mmodding.mmodding_lib.library.config.screen.ConfigElementsListWidget;
import com.mmodding.mmodding_lib.library.config.screen.ConfigScreen;

public class BooleanEditScreen extends EditScreen<Boolean> {

	public BooleanEditScreen(ConfigScreen lastScreen, ConfigElementsListWidget list, ConfigElementsListEntry entry, ConfigObject mutableConfig, String fieldName, ConfigObject.Value<Boolean> baseValue) {
		super(lastScreen, list, entry, mutableConfig, fieldName, baseValue);
	}

	@Override
	public void keepAndClose() {}
}
