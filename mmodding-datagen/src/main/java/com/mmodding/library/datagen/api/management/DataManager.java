package com.mmodding.library.datagen.api.management;

import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;

import java.util.function.Predicate;

public interface DataManager {

	/**
	 * Extracts elements of a specific type from a specified class to perform data generation associated to the content.
	 * @param source the class to extract contents from
	 * @param handler the data handler
	 * @param <T> the data class type
	 */
	<T> void task(Class<?> source, FinalDataHandler<T> handler);

	/**
	 * Extracts and processes contents of a specific type from a specified class to perform data generation associated to the content.
	 * @param source the class to extract contents from
	 * @param handler the data handler
	 * @param processor the data processor
	 * @param <T> the data class type
	 * @param <P> the data processor class type
	 */
	<T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, P processor);

	/**
	 * Extracts and processes filtered contents of a specific type from a specified class to perform data generation associated to the content.
	 * @param source the class to extract contents from
	 * @param handler the data handler
	 * @param filter the data filter
	 * @param processor the data processor
	 * @param <T> the data class type
	 * @param <P> the data processor class type
	 */
	<T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, Predicate<T> filter, P processor);

	/**
	 * Variant of the previous method which allows to chain filters to apply processors sequentially.
	 * An element getting caught at a step will stay there, whereas if not it will reach the next chain node, if any.
	 * @param source the class to extract contents from
	 * @param handler the data handler
	 * @return the chain manager
	 * @param <T> the data class type
	 * @param <P> the data processor class type
	 */
	<T, P> ChainManager<T, P> chain(Class<?> source, DataProcessHandler<T, P> handler);

	interface ChainManager<T, P> {

		/**
		 * Chains a new filter for elements reaching this point, and processes them.
		 * @param filter the filter
		 * @param processor the processor
		 * @return the chain manager
		 */
		ChainManager<T, P> chain(Predicate<T> filter, P processor);

		/**
		 * Ends the chain by processing the last unfiltered elements.
		 * @param processor the processor
		 */
		void chain(P processor);
	}
}
