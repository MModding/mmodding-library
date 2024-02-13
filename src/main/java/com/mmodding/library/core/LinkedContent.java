package com.mmodding.library.core;

public class LinkedContent<S, T> {

	private final S source;

	private T target = null;

	public LinkedContent(S source) {
		this.source = source;
	}

	public LinkedContent(S source, T target) {
		this(source);
		this.setTarget(target);
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public T retrieve() {
		return this.target;
	}
}
