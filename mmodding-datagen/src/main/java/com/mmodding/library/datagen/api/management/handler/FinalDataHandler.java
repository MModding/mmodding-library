package com.mmodding.library.datagen.api.management.handler;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.List;

/**
 * Represents a final type of data (to be handled as it is).
 * It is a particular case when the way of processing the data for all elements of the type.
 * @param <T> the data type
 */
public interface FinalDataHandler<T> extends DataHandler<T> {

	void handleContent(FabricDataGenerator.Pack pack, List<T> finalContents);
}
