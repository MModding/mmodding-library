package com.mmodding.library.datagen.api.management.handler;

/**
 * An abstraction to {@link FinalDataHandler} and {@link DataProcessHandler}.
 * @param <T> the class type
 */
public interface DataHandler<T> {

	Class<T> getType();
}
