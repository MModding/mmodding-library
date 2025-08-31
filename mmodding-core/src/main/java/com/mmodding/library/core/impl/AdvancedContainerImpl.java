package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.ModOrigin;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class AdvancedContainerImpl implements AdvancedContainer {

	private final ModContainer mod;

	public AdvancedContainerImpl(ModContainer mod) {
		this.mod = mod;
	}

	@Override
	public ModMetadata getMetadata() {
		return this.mod.getMetadata();
	}

	@Override
	public List<Path> getRootPaths() {
		return this.mod.getRootPaths();
	}

	@Override
	public ModOrigin getOrigin() {
		return this.mod.getOrigin();
	}

	@Override
	public Optional<ModContainer> getContainingMod() {
		return this.mod.getContainingMod();
	}

	@Override
	public Collection<ModContainer> getContainedMods() {
		return this.mod.getContainedMods();
	}

	@Override
	public Path getRootPath() {
		return this.mod.getRootPath();
	}

	@Override
	public Path getPath(String file) {
		return this.mod.getPath(file);
	}
}
