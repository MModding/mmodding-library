package com.mmodding.mmodding_lib.library.tags.modifiers;

import net.minecraft.tag.TagKey;
import org.quiltmc.qsl.base.api.util.TriState;

import java.util.function.Function;

public interface TagModifier<T> {

	static <T> SimpleTagModifier<T> ofSimple(TagKey<T> tagKey) {
		return new SimpleTagModifier<>(tagKey);
	}

	static <T> AdvancedTagModifier<T> ofAdvanced(TagKey<T> tagKey, AdvancedTagModifier.Priority priority) {
		return new AdvancedTagModifier<>(tagKey, priority);
	}

	TriState status(T value);

	boolean result(T value, Function<TagKey<T>, Boolean> backup);

	TagKey<T> getTagKey();
}
