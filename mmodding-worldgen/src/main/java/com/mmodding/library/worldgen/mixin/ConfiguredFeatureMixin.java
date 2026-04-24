package com.mmodding.library.worldgen.mixin;

import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.impl.feature.duck.ConfiguredFeatureReplicator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(ConfiguredFeature.class)
public class ConfiguredFeatureMixin<FC extends FeatureConfiguration> implements ConfiguredFeatureReplicator<FC> {

	@Shadow
	private FC config;

	@Unique
	private Pair<Holder<ConfiguredFeature<?, ?>>, AutoMapper<FC>> replicateTarget = null;

	@Inject(method = "config", at = @At("HEAD"))
	private void applyReplication(CallbackInfoReturnable<FC> cir) {
		this.possiblyReplicate();
	}

	@Inject(method = "place", at = @At("HEAD"))
	private void applyReplication(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin, CallbackInfoReturnable<Boolean> cir) {
		this.possiblyReplicate();
	}

	@Inject(method = "getSubFeatures", at = @At("HEAD"))
	private void applyReplicationSubFeatures(CallbackInfoReturnable<Stream<Holder<ConfiguredFeature<?, ?>>>> cir) {
		this.possiblyReplicate();
	}

	@Inject(method = "toString", at = @At("HEAD"))
	private void applyReplicationToString(CallbackInfoReturnable<String> cir) {
		this.possiblyReplicate();
	}

	@Override
	public void mmodding$replicate(Holder<ConfiguredFeature<?, ?>> target, AutoMapper<FC> patch) {
		this.replicateTarget = Pair.create(target, patch);
	}

	@Unique
	@SuppressWarnings("unchecked")
	private void possiblyReplicate() {
		if (this.replicateTarget != null) {
			this.config = this.replicateTarget.second().map((FC) this.replicateTarget.first().value().config());
		}
		this.replicateTarget = null;
	}
}
