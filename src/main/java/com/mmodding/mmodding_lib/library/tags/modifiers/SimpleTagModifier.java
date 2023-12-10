package com.mmodding.mmodding_lib.library.tags.modifiers;

import net.minecraft.tag.TagKey;
import org.quiltmc.qsl.base.api.util.TriState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class SimpleTagModifier<T> implements TagModifier<T> {

	private final TagKey<T> tagKey;
	private final List<T> additions;
	private final List<T> exclusions;

	SimpleTagModifier(TagKey<T> tagKey) {
		this.tagKey = tagKey;
		this.additions = new ArrayList<>();
		this.exclusions = new ArrayList<>();
	}

	private T check(T value) {
		if (this.additions.contains(value)) {
			throw new IllegalArgumentException("Value " + value + " is already an addition in this manager of " + this.tagKey);
		}
		else if (this.exclusions.contains(value)) {
			throw new IllegalArgumentException("Value " + value + " is already an exclusion in this manager of " + this.tagKey);
		}
		else {
			return value;
		}
	}

	@SafeVarargs
	public final SimpleTagModifier<T> append(T... values) {
		for (T value : values) {
			this.additions.add(this.check(value));
		}
		return this;
	}

	@SafeVarargs
	public final SimpleTagModifier<T> exclude(T... values) {
		for (T value : values) {
			this.exclusions.add(this.check(value));
		}
		return this;
	}

	public TriState status(T value) {
		if (this.additions.contains(value)) {
			return TriState.TRUE;
		}
		else if (this.exclusions.contains(value)) {
			return TriState.FALSE;
		}
		else {
			return TriState.DEFAULT;
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

	public TagKey<T> getTagKey() {
		return this.tagKey;
	}
}
