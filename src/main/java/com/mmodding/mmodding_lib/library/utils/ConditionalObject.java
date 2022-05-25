package com.mmodding.mmodding_lib.library.utils;

public interface ConditionalObject<T> {
    T create();

    T getIfCreated();

    T getIfCreatedOrElse(T object);
}
