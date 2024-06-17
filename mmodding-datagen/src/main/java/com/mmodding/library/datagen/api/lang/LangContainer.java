package com.mmodding.library.datagen.api.lang;

public interface LangContainer {

	default <T> T lang(LangProcessor<T> processor) {
		throw new IllegalStateException();
	}

	default Type type() {
		throw new IllegalStateException();
	}

	enum Type {
		ITEM,
		BLOCK,
		// GROUP, => disabled before registry exists
		ENTITY,
		ENCHANTMENT,
		ATTRIBUTE,
		STATISTIC
	}
}
