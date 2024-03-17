package com.mmodding.mmodding_lib.library.integrations.modmenu;

import com.mmodding.mmodding_lib.colors.Color;
import com.mmodding.mmodding_lib.library.utils.BiList;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.entrypoint.EntrypointContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class CustomBadge implements BadgeRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final BiList<String, Class<?>> entrypointInfo;
	private final Provider provider;
	private final Supplier<Color> outlineColor;
	private final Supplier<Color> fillColor;

	public CustomBadge(BiList<String, Class<?>> entrypointInfo, Provider provider, Color outlineColor, Color fillColor) {
		this(entrypointInfo, provider, () -> outlineColor, () -> fillColor);
	}

	public CustomBadge(BiList<String, Class<?>> entrypointInfo, Provider provider, Supplier<Color> outlineColor, Supplier<Color> fillColor) {
		this.entrypointInfo = entrypointInfo;
		this.provider = provider;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}

	public List<String> getMods() {
		List<String> mods = new ArrayList<>();
		this.entrypointInfo.forEach(
			(entrypointKey, entrypointClass) -> mods.addAll(this.provider.getMods(QuiltLoader.getEntrypointContainers(entrypointKey, entrypointClass)))
		);
		return mods;
	}

	public Color getOutlineColor() {
		return this.outlineColor.get();
	}

	public Color getFillColor() {
		return this.fillColor.get();
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}

	@FunctionalInterface
	public interface Provider {

		List<String> getMods(List<? extends EntrypointContainer<?>> entrypointContainers);
	}
}
