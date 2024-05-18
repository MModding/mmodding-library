package com.mmodding.library.java.api.object;

/**
 * This interface allows to retrieve the self instance under another class type.
 * It is very useful for mixin classes.
 * @param <S> the target class type
 */
public interface Self<S> {

	@SuppressWarnings("unchecked")
	default <T extends S> T getObject() {
		return (T) this;
	}
}
