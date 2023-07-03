package com.mmodding.mmodding_lib.mixin.injectors;

import com.google.common.collect.ImmutableList;
import com.mmodding.mmodding_lib.ducks.ChunkNoiseSamplerDuckInterface;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.mixin.accessors.ChunkNoiseSamplerAccessor;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.DensityFunctions;
import net.minecraft.world.gen.RandomState;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.material.MaterialRuleList;
import net.minecraft.world.gen.noise.NoiseRouter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkNoiseSampler.class)
public abstract class ChunkNoiseSamplerMixin implements ChunkNoiseSamplerDuckInterface {

	@Unique
	private Holder<ChunkGeneratorSettings> settingsHolder;

	@Unique
	private RandomState randomState;

	@Shadow
	protected abstract DensityFunction getWrappedFunction(DensityFunction function);

	@Shadow
	@Final
	private AquiferSampler aquiferSampler;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(int i, RandomState randomState, int j, int k, GenerationShapeConfig generationShapeConfig, DensityFunctions.StructureWeightSamplerOrMarker structureWeightSamplerOrMarker, ChunkGeneratorSettings chunkGeneratorSettings, AquiferSampler.FluidPicker fluidPicker, Blender blender, CallbackInfo ci) {
		this.randomState = randomState;
	}

	@Unique
	public void reloadBlockStateSampler() {

		ChunkNoiseSampler thisSampler = (ChunkNoiseSampler) (Object) this;

		Holder<ChunkGeneratorSettings> holder = this.getSettingsHolder();

		holder.getKey().ifPresent(key -> {
			Identifier id = key.getValue();
			if (MModdingGlobalMaps.hasCustomVeinTypes(id)) {

				NoiseRouter router = this.randomState.getRouter().m_vekvzbgt(this::getWrappedFunction);

				ImmutableList.Builder<ChunkNoiseSampler.BlockStateSampler> builder = ImmutableList.builder();

				DensityFunction densityFunction = DensityFunctions.cacheAllInCell(
					DensityFunctions.add(router.fullNoise(), DensityFunctions.StructureWeightSamplerMarker.INSTANCE)
				).mapAll(this::getWrappedFunction);

				builder.add(functionContext -> this.aquiferSampler.apply(functionContext, densityFunction.compute(functionContext)));

				MModdingGlobalMaps.getCustomVeinTypes(id).forEach(customVeinType ->
					builder.add(customVeinType.createCustomVein(router.veinToggle(), router.veinRidged(), router.veinGap(), this.randomState.getOreRandomFactory()))
				);

				((ChunkNoiseSamplerAccessor) thisSampler).setBlockStateSampler(new MaterialRuleList(builder.build()));
			}
		});
	}

	@Override
	public Holder<ChunkGeneratorSettings> getSettingsHolder() {
		return this.settingsHolder;
	}

	@Override
	public void setSettingsHolder(Holder<ChunkGeneratorSettings> holder) {
		this.settingsHolder = holder;
	}
}
