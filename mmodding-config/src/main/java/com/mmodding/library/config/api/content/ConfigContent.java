package com.mmodding.library.config.api.content;

import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface ConfigContent {

	boolean bool(String qualifier);

	int integer(String qualifier);

	float floating(String qualifier);

	String string(String qualifier);

	Color color(String qualifier);

	int integerRange(String qualifier);

	float floatingRange(String qualifier);

	MixedList list(String qualifier);

	ConfigContent category(String qualifier);
}
