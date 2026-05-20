package com.mmodding.library.config.api.exception;

import com.mmodding.library.config.api.content.ConfigContent;

/**
 * An exception occurring on {@link ConfigContent} network decoding.
 */
public class ConfigNetworkDecodingException extends RuntimeException {

	public ConfigNetworkDecodingException(String message) {
		super(message);
	}
}
