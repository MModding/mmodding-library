package com.mmodding.library.config.api.content;

import com.mmodding.library.config.api.content.builtin.FloatingRange;
import com.mmodding.library.config.api.content.builtin.IntegerRange;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.BiList;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.NonExtendable
public interface ConfigContent {

	boolean bool(String property);

	int intValue(String property);

	double doubleValue(String property);

	String string(String property);

	Color color(String property);

	IntegerRange integerRange(String property);

	FloatingRange floatingRange(String property);

	<T extends Enum<T>> Enum<T> enumValue(String property);

	<T> List<T> list(String property);

	<K, V> Map<K, V> map(String property);

	<T> T element(String property, Class<T> type);

	ConfigContent category(String property);

	BiList<String, Class<?>> getAllProperties();
}
