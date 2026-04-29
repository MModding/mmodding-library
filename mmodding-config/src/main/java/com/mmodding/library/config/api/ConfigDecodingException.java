package com.mmodding.library.config.api;

import com.mmodding.library.config.api.content.ConfigContent;

/**
 * An exception occurring on {@link ConfigContent} decoding.
 */
public class ConfigDecodingException extends RuntimeException {

	public ConfigDecodingException(String message) {
		super(message);
	}
}
