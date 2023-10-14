package com.mmodding.mmodding_lib.library.integrations.modmenu;

import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.Colors;
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
	private final Supplier<Colors.RGB> outlineColor;
	private final Supplier<Colors.RGB> fillColor;

	public CustomBadge(BiList<String, Class<?>> entrypointInfo, Provider provider, Colors.RGB outlineColor, Colors.RGB fillColor) {
		this(entrypointInfo, provider, () -> outlineColor, () -> fillColor);
	}

	public CustomBadge(BiList<String, Class<?>> entrypointInfo, Provider provider, Supplier<Colors.RGB> outlineColor, Supplier<Colors.RGB> fillColor) {
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

	public Colors.RGB getOutlineColor() {
		return this.outlineColor.get();
	}

	public Colors.RGB getFillColor() {
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
