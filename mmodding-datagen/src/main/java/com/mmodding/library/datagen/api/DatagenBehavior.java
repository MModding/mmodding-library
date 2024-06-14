package com.mmodding.library.datagen.api;

import com.mmodding.library.datagen.api.lang.LangProcessor;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface DatagenBehavior {

	LangProcessor<?> getLangProcessor();
}
