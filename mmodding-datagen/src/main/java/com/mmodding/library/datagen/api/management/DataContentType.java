package com.mmodding.library.datagen.api.management;

import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.List;

/**
 * Represents a type of data to be processed.
 * @param <T> the data type
 * @param <P> the data processor type
 */
public interface DataContentType<T, P> {

	void handleContent(FabricDataGenerator.Pack pack, BiList<P, List<T>> contentToProcess);
}
