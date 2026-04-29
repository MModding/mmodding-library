package com.mmodding.library.config.api.content;

import com.mmodding.library.config.api.content.context.DoubleRangeContext;
import com.mmodding.library.config.api.content.context.IntRangeContext;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.NonExtendable
public interface ConfigContent {

	static String resolve(String parent, String property) {
		return (!parent.isEmpty() ? parent + "." : "") + property;
	}

	/**
	 * The {@link ConfigSchema} ruling over the content.
	 * @return the schema
	 */
	ConfigSchema schema();

	/**
	 * The property path of the current content.
	 * <br>Convention for property paths is: <code>i.am.a.property.path</code>.
	 * @return the property path
	 */
	String path();

	/**
	 * Returns context for the property, or throws if there's none.
	 * @param property the property
	 * @return the context
	 * @param <T> the class type of the context
	 */
	default <T extends ConfigSchemaNode.Context> T context(String property) {
		return this.schema().findContextOrThrow(resolve(this.path(), property));
	}

	default boolean bool(String property) {
		return this.element(property, Boolean.class);
	}

	default int intValue(String property) {
		return this.element(property, Integer.class);
	}

	default double doubleValue(String property) {
		return this.element(property, Double.class);
	}

	default String string(String property) {
		return this.element(property, String.class);
	}

	default Identifier identifier(String property) {
		return this.element(property, Identifier.class);
	}

	default Color color(String property) {
		return this.element(property, Color.class);
	}

	default BlockPos pos(String property) {
		return this.element(property, BlockPos.class);
	}

	default Vec3 vec3(String property) {
		return this.element(property, Vec3.class);
	}

	default Pair<Integer, IntRangeContext> intRange(String property) {
		return this.elementWithContext(property, Integer.class);
	}

	default Pair<Double, DoubleRangeContext> doubleRange(String property) {
		return this.elementWithContext(property, Double.class);
	}

	default <T extends Enum<T>> Enum<T> enumValue(String property, Class<?> enumClass) {
		return this.element(property, enumClass);
	}

	default <T> List<T> list(String property) {
		return this.element(property, List.class);
	}

	default <K, V> Map<K, V> map(String property) {
		return this.element(property, Map.class);
	}

	default <T, C extends ConfigSchemaNode.Context> Pair<T, C> elementWithContext(String property, Class<?> type) {
		return Pair.create(this.element(property, type), this.context(property));
	}

	<T> T element(String property, Class<?> type);

	ConfigContent category(String property);

	BiList<String, Class<?>> getAllProperties();
}
