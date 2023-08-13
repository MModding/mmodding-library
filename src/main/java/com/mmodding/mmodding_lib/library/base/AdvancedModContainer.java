package com.mmodding.mmodding_lib.library.base;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public interface AdvancedModContainer extends ModContainer {

	static AdvancedModContainer of(ModContainer mod) {

		return new AdvancedModContainer() {

			@Override
			public ModMetadata metadata() {
				return mod.metadata();
			}

			@Override
			public Path rootPath() {
				return mod.rootPath();
			}

			@Override
			public List<List<Path>> getSourcePaths() {
				return mod.getSourcePaths();
			}

			@Override
			public BasicSourceType getSourceType() {
				return mod.getSourceType();
			}
		};
	}

	default Logger getLogger() {
		return LoggerFactory.getLogger(this.metadata().name());
	}
}
