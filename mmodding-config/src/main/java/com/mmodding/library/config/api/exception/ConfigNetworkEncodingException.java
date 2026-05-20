package com.mmodding.library.config.api.exception;

import com.mmodding.library.config.api.content.ConfigContent;

/**
 * An exception occurring on {@link ConfigContent} network encoding.
 */
public class ConfigNetworkEncodingException extends RuntimeException {

	public ConfigNetworkEncodingException(String message) {
		super(message);
	}
}
