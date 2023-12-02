package com.mmodding.mmodding_lib.library.network.request.common;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import org.jetbrains.annotations.ApiStatus;

@FunctionalInterface
public interface RequestAction<T extends NetworkSupport> {

	@ApiStatus.NonExtendable
	@SuppressWarnings("unchecked")
	default void cast(NetworkSupport received) {
		this.end((T) received);
	}

	void end(T received);
}
