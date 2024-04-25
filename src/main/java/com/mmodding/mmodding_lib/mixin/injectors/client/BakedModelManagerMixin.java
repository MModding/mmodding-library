package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.ducks.BakedModelManagerDuckInterface;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerDuckInterface {

	@Shadow
	private Map<Identifier, BakedModel> models;

	@Shadow
	private BakedModel missingModel;

	@Override
	public BakedModel mmodding_lib$getModel(Identifier identifier) {
		return this.models.getOrDefault(identifier, this.missingModel);
	}
}
