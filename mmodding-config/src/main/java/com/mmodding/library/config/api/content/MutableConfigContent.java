package com.mmodding.library.config.api.content;

import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MutableConfigContent {

	MutableConfigContent bool(String qualifier, boolean bool);

	MutableConfigContent integer(String qualifier, int integer);

	MutableConfigContent floating(String qualifier, float floating);

	MutableConfigContent string(String qualifier, String string);

	MutableConfigContent color(String qualifier, Color color);

	MutableConfigContent integerRange(String qualifier, int integer);

	MutableConfigContent floatingRange(String qualifier, float floating);

	MutableConfigContent list(String qualifier, MixedList list);

	MutableConfigContent category(String qualifier, MutableConfigContent category);
}
