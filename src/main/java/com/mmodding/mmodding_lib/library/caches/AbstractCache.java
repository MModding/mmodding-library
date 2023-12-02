package com.mmodding.mmodding_lib.library.caches;

import java.util.HashMap;

public abstract class AbstractCache<K, V> extends HashMap<K, V> {

	protected final String cache;
	protected final String key;
	protected final String value;

	AbstractCache(String cache, String key, String value) {
		this.cache = cache;
		this.key = key;
		this.value = value;
	}

	public abstract boolean clientReserved();

	public void debug() {
		System.out.println((this.clientReserved() ? "Client " : "Local ") + "Cache {" + this.cache + "} :");
		this.forEach((key, value) -> System.out.println(
			"- " + this.key + " : " + key + " | " + this.value + " : " + value
		));
	}
}
