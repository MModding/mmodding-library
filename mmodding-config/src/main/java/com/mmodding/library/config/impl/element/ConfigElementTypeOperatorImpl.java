package com.mmodding.library.config.impl.element;

import com.mmodding.library.config.api.element.type.ConfigElementTypeOperator;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.impl.content.MutableConfigContentImpl;
import com.mmodding.library.java.api.color.Color;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class ConfigElementTypeOperatorImpl {

	public static final Map<Class<?>, ConfigElementTypeOperator> OPERATORS = new Object2ObjectOpenHashMap<>();

	public static final ConfigElementTypeOperator BUILTIN_COLOR = new ConfigElementTypeOperator(
		(category, properties) -> (qualifier, mutable) -> mutable.color(qualifier, Color.rgb(category.integer(qualifier))),
		(writer, category, qualifier) -> writer.value(category.color(qualifier).toDecimal())
	);

	public static final ConfigElementTypeOperator BUILTIN_INTEGER_RANGE = new ConfigElementTypeOperator(
		(category, properties) -> (qualifier, mutable) -> {
			MutableConfigContentImpl impl = (MutableConfigContentImpl) mutable;
			impl.initIntegerRange(qualifier, new IntegerRange(properties.get("min"), properties.get("max"), category.integer(qualifier)));
		},
		(writer, category, qualifier) -> writer.value(category.integerRange(qualifier).getValue())
	);

	public static final ConfigElementTypeOperator BUILTIN_FLOATING_RANGE = new ConfigElementTypeOperator(
		(category, properties) -> (qualifier, mutable) -> {
			MutableConfigContentImpl impl = (MutableConfigContentImpl) mutable;
			impl.initFloatingRange(qualifier, new FloatingRange(properties.get("min"), properties.get("max"), category.floating(qualifier)));
		},
		(writer, category, qualifier) -> writer.value(category.floatingRange(qualifier).getValue())
	);

	public static void registerBuiltin() {
		ConfigElementTypeOperator.register(Color.class, ConfigElementTypeOperatorImpl.BUILTIN_COLOR);
		ConfigElementTypeOperator.register(IntegerRange.class, ConfigElementTypeOperatorImpl.BUILTIN_INTEGER_RANGE);
		ConfigElementTypeOperator.register(FloatingRange.class, ConfigElementTypeOperatorImpl.BUILTIN_FLOATING_RANGE);
	}
}
