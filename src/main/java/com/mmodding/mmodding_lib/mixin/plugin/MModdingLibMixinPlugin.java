package com.mmodding.mmodding_lib.mixin.plugin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MModdingLibMixinPlugin implements IMixinConfigPlugin {

	private static final Set<String> FABRIC_ONLY_MIXIN = Set.of(
		"injectors.FabricEntityTypeBuilderMixin",
		"injectors.FabricEntityTypeBuilderMixin$Living",
		"injectors.FabricEntityTypeBuilderMixin$Mob"
	);

	private static final Set<String> QUILT_ONLY_MIXIN = Set.of(
		"injectors.QuiltEntityTypeBuilderMixin",
		"injectors.QuiltEntityTypeBuilderMixin$Builder",
		"injectors.QuiltEntityTypeBuilderMixin$Living",
		"injectors.QuiltEntityTypeBuilderMixin$FabricBuilder",
		"injectors.QuiltEntityTypeBuilderMixin$FabricLiving",
		"injectors.QuiltEntityTypeBuilderMixin$FabricMob"
	);

	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		String mixinName = mixinClassName.replace("com.mmodding.mmodding_lib.mixin.", "");
		if (FabricLoader.getInstance().isModLoaded("quilt_loader")) {
			return !FABRIC_ONLY_MIXIN.contains(mixinName);
		}
		else {
			return !QUILT_ONLY_MIXIN.contains(mixinName);
		}
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
