package com.mmodding.mmodding_lib.library.utils;

public interface Registrable {

	/**
	 * @return if the Registrable element is not registered
	 */
    boolean isNotRegistered();

	/**
	 * Set the Registrable element as registered
	 */
    void setRegistered();
}
