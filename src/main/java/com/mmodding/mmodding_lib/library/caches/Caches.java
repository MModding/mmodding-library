package com.mmodding.mmodding_lib.library.caches;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;

public class Caches {

	public abstract static class Cache<K, V> extends HashMap<K, V> {

		protected final String cache;
		protected final String key;
		protected final String value;

		private Cache(String cache, String key, String value) {
			this.cache = cache;
			this.key = key;
			this.value = value;
		}

		abstract public boolean clientReserved();

		public void debug() {
			System.out.println((this.clientReserved() ? "Client " : "Local ") + "Cache {" + this.cache + "} :");
			this.forEach((key, value) -> System.out.println(
				"- " + this.key + " : " + key + " | " + this.value + " : " + value
			));
		}
	}

	public static class Local<K, V> extends Cache<K, V> {

		public Local(String cache, String key, String value) {
			super(cache, key, value);
		}

		@Override
		public boolean clientReserved() {
			return false;
		}
	}

	@ClientOnly
	public static class Client<K, V> extends Cache<K, V> {

		public Client(String cache, String key, String value) {
			super(cache, key, value);
		}

		@Override
		public boolean clientReserved() {
			return true;
		}
	}
}
