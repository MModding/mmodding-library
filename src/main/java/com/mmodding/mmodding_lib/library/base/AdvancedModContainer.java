package com.mmodding.mmodding_lib.library.base;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.ModOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AdvancedModContainer extends ModContainer {

	static AdvancedModContainer of(ModContainer mod) {

		return new AdvancedModContainer() {

			@Override
			public ModMetadata getMetadata() {
				return mod.getMetadata();
			}

			@Override
			public List<Path> getRootPaths() {
				return mod.getRootPaths();
			}

			@Override
			public ModOrigin getOrigin() {
				return mod.getOrigin();
			}

			@Override
			public Optional<ModContainer> getContainingMod() {
				return mod.getContainingMod();
			}

			@Override
			public Collection<ModContainer> getContainedMods() {
				return mod.getContainedMods();
			}

			@Override
			public Path getRootPath() {
				return mod.getRootPath();
			}

			@Override
			public Path getPath(String file) {
				return mod.getPath(file);
			}
		};
	}

	default Logger getLogger() {
		return LoggerFactory.getLogger(this.getMetadata().getName());
	}
}
