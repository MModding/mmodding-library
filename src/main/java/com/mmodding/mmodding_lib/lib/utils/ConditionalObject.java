package com.mmodding.mmodding_lib.lib.utils;

public interface ConditionalObject<T> {
    T create();

    T getIfCreated();

    T getIfCreatedOrElse(T object);
}
