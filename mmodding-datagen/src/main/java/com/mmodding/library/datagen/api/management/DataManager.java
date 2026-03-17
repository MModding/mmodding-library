package com.mmodding.library.datagen.api.management;

import java.util.function.Predicate;

public interface DataManager {

	/**
	 * Extracts and processes contents of a specific type from a specified class to perform data generation associated to the content.
	 * @param sourceClass the class to extract contents from
	 * @param type the type of content to extract
	 * @param handler the data handler
	 * @param processor the data processor
	 * @return the manager
	 * @param <T> the data class type
	 * @param <P> the data processor class type
	 */
	<T, P> DataManager data(Class<?> sourceClass, Class<T> type, DataContentType<T, P> handler, P processor);

	/**
	 * Extracts and processes filtered contents of a specific type from a specified class to perform data generation associated to the content.
	 * @param sourceClass the class to extract contents from
	 * @param type the type of content to extract
	 * @param filter the data filter
	 * @param handler the data handler
	 * @param processor the data processor
	 * @return the manager
	 * @param <T> the data class type
	 * @param <P> the data processor class type
	 */
	<T, P> DataManager data(Class<?> sourceClass, Class<T> type, Predicate<T> filter, DataContentType<T, P> handler, P processor);
}
