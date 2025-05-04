package com.mmodding.mmodding_lib.library.caches;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

public class Caches {

	public static final List<CacheAccess> LOCAL = new ArrayList<>();

	@Environment(EnvType.CLIENT)
	public static final List<CacheAccess> CLIENT = new ArrayList<>();

	public static class Local<K, V> extends AbstractCache<K, V> {

		public Local(String cache, String key, String value) {
			super(cache, key, value);
			LOCAL.add(CacheAccess.ofCache(this));
		}

		@Override
		public boolean clientReserved() {
			return false;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class Client<K, V> extends AbstractCache<K, V> {

		public Client(String cache, String key, String value) {
			super(cache, key, value);
			CLIENT.add(CacheAccess.ofCache(this));
		}

		@Override
		public boolean clientReserved() {
			return true;
		}
	}
}
