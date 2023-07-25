package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.AbstractBlockSettingsDuckInterface;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractBlock.Settings.class)
public class AbstractBlockSettingsMixin implements AbstractBlockSettingsDuckInterface {

	@Unique
	boolean translucent = false;

	@Unique
	boolean notTranslucent = false;

	@Unique
	boolean invisibleSides = false;

	@Override
	public boolean getTranslucent() {
		return this.translucent;
	}

	@Override
	public boolean getNotTranslucent() {
		return this.notTranslucent;
	}

	@Override
	public boolean getInvisibleSides() {
		return this.invisibleSides;
	}

	@Override
	public void setTranslucent(boolean transparent) {
		this.translucent = transparent;
	}

	@Override
	public void setNotTranslucent(boolean notTranslucent) {
		this.notTranslucent = notTranslucent;
	}

	@Override
	public void setInvisibleSides(boolean invisibleSides) {
		this.invisibleSides = invisibleSides;
	}
}
