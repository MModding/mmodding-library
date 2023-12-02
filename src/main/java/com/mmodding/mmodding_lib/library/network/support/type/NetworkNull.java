package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;

public class NetworkNull<T> extends NetworkPrimitive<T> implements NetworkSupport {

	NetworkNull() {
		super(null, (buf, object) -> {});
	}
}
