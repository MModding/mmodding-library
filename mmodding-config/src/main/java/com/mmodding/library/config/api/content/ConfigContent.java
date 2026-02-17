package com.mmodding.library.config.api.content;

import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface ConfigContent {

	boolean bool(String qualifier);

	int integer(String qualifier);

	double floating(String qualifier);

	String string(String qualifier);

	Color color(String qualifier);

	IntegerRange integerRange(String qualifier);

	FloatingRange floatingRange(String qualifier);

	<T extends Enum<T>> Enum<T> enumValue(String qualifier);

	MixedList list(String qualifier);

	ConfigContent category(String qualifier);

	<T> T custom(String qualifier, Class<T> type);
}
