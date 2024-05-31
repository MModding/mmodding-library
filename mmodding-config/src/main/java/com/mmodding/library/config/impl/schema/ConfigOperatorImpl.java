package com.mmodding.library.config.impl.schema;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.content.ConfigContentImpl;
import com.mmodding.library.config.impl.content.MutableConfigContentImpl;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.map.BiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class ConfigOperatorImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger("mmodding_config");

	private static final Predicate<Class<?>> TYPES_TO_PATCH = type -> type == Color.class || type == IntStream.class || type == DoubleStream.class;

	private static boolean containsQualifier(BiMap<String, Class<?>, Map<String, ?>> rawSchema, String qualifier) {
		return rawSchema.containsKey(qualifier);
	}

	private static boolean isCategory(BiMap<String, Class<?>, Map<String, ?>> rawSchema, String qualifier) {
		return ConfigOperatorImpl.getSchemaType(rawSchema, qualifier) == ConfigSchema.class;
	}

	private static Class<?> getSchemaType(BiMap<String, Class<?>, Map<String, ?>> rawSchema, String qualifier) {
		return rawSchema.getFirstValue(qualifier);
	}

	private static boolean needFix(BiMap<String, Class<?>, Map<String, ?>> rawSchema, ConfigContent content, String qualifier) {
		Class<?> type = ConfigOperatorImpl.getSchemaType(rawSchema, qualifier);
		return TYPES_TO_PATCH.test(type) && type != ((ConfigContentImpl) content).getRaw().get(qualifier).getType();
	}

	@SuppressWarnings("unchecked")
	private static void applyCategory(BiMap<String, Class<?>, Map<String, ?>> rawSchema, ConfigContent content, MutableConfigContent mutable) {
		for (String qualifier : ((ConfigContentImpl) content).getRaw().keySet()) {
			if (ConfigOperatorImpl.containsQualifier(rawSchema, qualifier)) {
				if (ConfigOperatorImpl.isCategory(rawSchema, qualifier)) {
					mutable.category(qualifier, nextMutable -> ConfigOperatorImpl.applyCategory(
						(BiMap<String, Class<?>, Map<String,?>>) rawSchema.getSecondValue(qualifier),
						content.category(qualifier),
						nextMutable
					));
				}
				else if (ConfigOperatorImpl.needFix(rawSchema, content, qualifier)) {
					if (ConfigOperatorImpl.getSchemaType(rawSchema, qualifier) == Color.class) {
						mutable.color(qualifier, Color.rgb(content.integer(qualifier)));
					}
					else if (ConfigOperatorImpl.getSchemaType(rawSchema, qualifier) == IntStream.class) {
						Map<String, ?> properties = rawSchema.getSecondValue(qualifier);
						mutable.integerRange(qualifier, Math.max((int) properties.get("start"), Math.min((int) properties.get("end"), content.integer(qualifier))));
					}
					else if (ConfigOperatorImpl.getSchemaType(rawSchema, qualifier) == DoubleStream.class) {
						Map<String, ?> properties = rawSchema.getSecondValue(qualifier);
						mutable.floatingRange(qualifier, Math.max((float) properties.get("start"), Math.min((float) properties.get("end"), content.floating(qualifier))));
					}
				}
			}
			else {
				ConfigOperatorImpl.LOGGER.warn("Unknown configuration qualifier \"{}\", present in the configuration file but not in the configuration schema!", qualifier);
			}
		}
	}

	public static ConfigContent applySchema(ConfigSchema schema, ConfigContent content) {
		if (!(schema instanceof ConfigSchemaImpl.EmptySchema)) {
			MutableConfigContentImpl mutable = new MutableConfigContentImpl(content);
			ConfigOperatorImpl.applyCategory(((ConfigSchemaImpl) schema).raw, content, mutable);
			return mutable.immutable();
		}
		else {
			return content;
		}
	}
}
