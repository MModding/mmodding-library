package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.AbstractBlockSettingsDuckInterface;
import net.minecraft.block.AbstractBlock;
import org.quiltmc.qsl.base.api.util.TriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractBlock.Settings.class)
public class AbstractBlockSettingsMixin implements AbstractBlockSettingsDuckInterface {

	@Unique
	TriState translucent = TriState.DEFAULT;

	@Unique
	boolean invisibleSides = false;

	@Override
	public TriState mmodding_lib$getTranslucent() {
		return this.translucent;
	}

	@Override
	public boolean mmodding_lib$getInvisibleSides() {
		return this.invisibleSides;
	}

	@Override
	public void mmodding_lib$setTranslucent(TriState transparent) {
		this.translucent = transparent;
	}

	@Override
	public void mmodding_lib$setInvisibleSides(boolean invisibleSides) {
		this.invisibleSides = invisibleSides;
	}
}
