package com.mmodding.library.config.impl.schema;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.element.type.ConfigElementTypeResolver;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.content.ConfigContentImpl;
import com.mmodding.library.config.impl.content.MutableConfigContentImpl;
import com.mmodding.library.config.impl.element.ConfigElementTypeOperatorImpl;
import com.mmodding.library.config.impl.element.ResolverPropertiesImpl;
import com.mmodding.library.java.api.map.BiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.BiConsumer;

public class ConfigOperator {

	private static final Logger LOGGER = LoggerFactory.getLogger("mmodding_config");

	private static boolean containsQualifier(BiMap<String, Class<?>, Map<String, ?>> rawSchema, String qualifier) {
		return rawSchema.containsKey(qualifier);
	}

	private static boolean isCategory(BiMap<String, Class<?>, Map<String, ?>> rawSchema, String qualifier) {
		return ConfigOperator.getSchemaType(rawSchema, qualifier) == ConfigSchema.class;
	}

	private static Class<?> getSchemaType(BiMap<String, Class<?>, Map<String, ?>> rawSchema, String qualifier) {
		return rawSchema.getFirstValue(qualifier);
	}

	private static boolean needFix(BiMap<String, Class<?>, Map<String, ?>> rawSchema, ConfigContent content, String qualifier) {
		Class<?> type = ConfigOperator.getSchemaType(rawSchema, qualifier);
		return ConfigElementTypeOperatorImpl.OPERATORS.containsKey(type) && type != ((ConfigContentImpl) content).getRaw().get(qualifier).getType();
	}

	@SuppressWarnings("unchecked")
	private static void applyCategory(BiMap<String, Class<?>, Map<String, ?>> rawSchema, ConfigContent content, MutableConfigContent mutable) {
		for (String qualifier : ((ConfigContentImpl) content).getRaw().keySet()) {
			if (ConfigOperator.containsQualifier(rawSchema, qualifier)) {
				if (ConfigOperator.isCategory(rawSchema, qualifier)) {
					mutable.category(qualifier, nextMutable -> ConfigOperator.applyCategory(
						(BiMap<String, Class<?>, Map<String,?>>) rawSchema.getSecondValue(qualifier),
						content.category(qualifier),
						nextMutable
					));
				}
				else if (ConfigOperator.needFix(rawSchema, content, qualifier)) {
					ConfigElementTypeResolver resolver = ConfigElementTypeOperatorImpl.OPERATORS.get(ConfigOperator.getSchemaType(rawSchema, qualifier)).resolver();
					BiConsumer<String, MutableConfigContent> applier = resolver.resolve(content, new ResolverPropertiesImpl(rawSchema.getSecondValue(qualifier)));
					applier.accept(qualifier, mutable);
				}
			}
			else {
				ConfigOperator.LOGGER.warn("Unknown configuration qualifier \"{}\", present in the configuration file but not in the configuration schema!", qualifier);
			}
		}
	}

	public static ConfigContent applySchema(ConfigSchema schema, ConfigContent content) {
		if (!ConfigSchemaImpl.isEmpty(schema)) {
			MutableConfigContentImpl mutable = new MutableConfigContentImpl(content);
			ConfigOperator.applyCategory(((ConfigSchemaImpl) schema).raw, content, mutable);
			return mutable.immutable();
		}
		else {
			return content;
		}
	}
}
