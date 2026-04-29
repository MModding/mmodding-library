package com.mmodding.library.config.api;

import com.mmodding.library.config.api.content.ConfigContent;

/**
 * An exception occurring on {@link ConfigContent} encoding.
 */
public class ConfigEncodingException extends RuntimeException {

	public ConfigEncodingException(String message) {
		super(message);
	}
}
