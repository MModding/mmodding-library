package com.mmodding.mmodding_lib.library.tags.modifiers;

import net.minecraft.tag.TagKey;
import org.quiltmc.qsl.base.api.util.TriState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class AdvancedTagModifier<T> implements TagModifier<T> {

	private final TagKey<T> tagKey;
	private final Priority priority;
	private final List<Function<T, Boolean>> additions;
	private final List<Function<T, Boolean>> exclusions;

	AdvancedTagModifier(TagKey<T> tagKey, Priority priority) {
		this.tagKey = tagKey;
		this.priority = priority;
		this.additions = new ArrayList<>();
		this.exclusions = new ArrayList<>();
	}

	private boolean process(List<Function<T, Boolean>> functions, T value) {
		for (Function<T, Boolean> function : functions) {
			if (function.apply(value)) {
				return true;
			}
		}
		return false;
	}

	@SafeVarargs
	public final AdvancedTagModifier<T> append(Function<T, Boolean>... functions) {
		this.additions.addAll(Arrays.asList(functions));
		return this;
	}

	@SafeVarargs
	public final AdvancedTagModifier<T> exclude(Function<T, Boolean>... functions) {
		this.exclusions.addAll(Arrays.asList(functions));
		return this;
	}

	@Override
	public TriState status(T value) {
		boolean addition = this.process(this.additions, value);
		boolean exclusion = this.process(this.exclusions, value);
		if (addition && exclusion) {
			if (this.priority.equals(Priority.ADDITION)) {
				return TriState.TRUE;
			}
			else if (this.priority.equals(Priority.EXCLUSION)) {
				return TriState.FALSE;
			}
			else {
				throw new RuntimeException("Unknown Enum Constant Of Priority");
			}
		}
		else {
			if (addition) {
				return TriState.TRUE;
			}
			else if (exclusion) {
				return TriState.FALSE;
			}
			else {
				return TriState.DEFAULT;
			}
		}
	}

	@Override
	public boolean result(T value, Function<TagKey<T>, Boolean> backup) {
		return switch (this.status(value)) {
			case TRUE -> true;
			case FALSE -> false;
			case DEFAULT -> backup.apply(this.tagKey);
		};
	}

	@Override
	public TagKey<T> getTagKey() {
		return this.tagKey;
	}

	/**
	 * If a value is considered as an addition and an exclusion at the same time,
	 * this enumeration will determine what will be the final decision.
	 */
	public enum Priority {
		ADDITION,
		EXCLUSION
	}
}
