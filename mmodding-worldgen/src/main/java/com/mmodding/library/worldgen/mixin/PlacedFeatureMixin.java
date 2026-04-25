package com.mmodding.library.worldgen.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import com.mmodding.library.worldgen.impl.feature.duck.PlacedFeatureReplicator;
import com.mmodding.library.worldgen.impl.feature.replication.PlacementModifiersImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlacedFeature.class)
public class PlacedFeatureMixin implements PlacedFeatureReplicator {

	@Shadow
	private List<PlacementModifier> placement;

	@Unique
	private Pair<Holder<PlacedFeature>, AutoMapper<PlacementModifiers>> replicateTarget = null;

	@WrapMethod(method = "lambda$static$2")
	private static List<PlacementModifier> useMethodInstead(PlacedFeature c, Operation<Holder<PlacedFeature>> original) {
		return c.placement();
	}

	@Inject(method = "placement", at = @At("HEAD"))
	private void applyReplication(CallbackInfoReturnable<List<PlacedFeature>> cir) {
		this.possiblyReplicate();
	}

	@Inject(method = "placeWithContext", at = @At("HEAD"))
	private void applyReplication(PlacementContext context, RandomSource random, BlockPos origin, CallbackInfoReturnable<Boolean> cir) {
		this.possiblyReplicate();
	}

	@Override
	public void mmodding$replicate(Holder<PlacedFeature> target, AutoMapper<PlacementModifiers> patch) {
		this.replicateTarget = Pair.create(target, patch);
	}

	@Unique
	private void possiblyReplicate() {
		if (this.replicateTarget != null) {
			PlacementModifiersImpl input = new PlacementModifiersImpl(new ArrayList<>(this.replicateTarget.first().value().placement()));
			PlacementModifiersImpl output = (PlacementModifiersImpl) this.replicateTarget.second().map(input);
			this.placement = output.retrieve();
		}
		this.replicateTarget = null;
	}
}
