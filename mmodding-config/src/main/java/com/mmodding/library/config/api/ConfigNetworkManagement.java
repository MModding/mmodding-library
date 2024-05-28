package com.mmodding.library.config.api;

public enum ConfigNetworkManagement {

	/**
	 * The configuration is always locally managed.
	 */
	LOCALLY_MANAGED,

	/**
	 * The configuration will use the upstream
	 * server configuration when present on a
	 * server. Otherwise, it will act the same
	 * as LOCALLY_MANAGED.
	 */
	UPSTREAM_SERVER
}
