package com.mmodding.mmodding_lib.library.debug;

import java.util.Map;
import java.util.function.Supplier;

/**
 * An interface used for providing watchers.
 * @implNote Currently only working with the "/mmodding watch" for entities, but I'll open it more in future versions.
 * It will only work with integrated servers.
 */
public interface WatcherProvider {

	/**
	 * @return a map associating a label to a value getter
	 */
	Map<String, Supplier<Object>> watcher();
}
