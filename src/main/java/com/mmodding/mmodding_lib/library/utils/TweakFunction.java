package com.mmodding.mmodding_lib.library.utils;

@FunctionalInterface
public interface TweakFunction<T> {

	T apply(T t);
}
