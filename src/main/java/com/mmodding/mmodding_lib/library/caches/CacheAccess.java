package com.mmodding.mmodding_lib.library.caches;

public class CacheAccess {

	private final Runnable debug;
	private final Runnable clear;

	private CacheAccess(Runnable debug, Runnable clear) {
		this.debug = debug;
		this.clear = clear;
	}

	public static <K, V> CacheAccess ofCache(AbstractCache<K, V> cache) {
		return new CacheAccess(cache::debug, cache::clear);
	}

	public void debugCache() {
		this.debug.run();
	}

	public void clearCache() {
		this.clear.run();
	}
}
