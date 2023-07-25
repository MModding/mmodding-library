package com.mmodding.mmodding_lib.library.blocks.settings;

import com.mmodding.mmodding_lib.ducks.AbstractBlockSettingsDuckInterface;
import com.mmodding.mmodding_lib.mixin.accessors.BlocksAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class BlockSettingsModifiers {

	private final QuiltBlockSettings settings;

	private BlockSettingsModifiers(QuiltBlockSettings settings) {
		this.settings = settings;
	}

	public static BlockSettingsModifiers ofSettings(QuiltBlockSettings settings) {
		return new BlockSettingsModifiers(settings);
	}

	public QuiltBlockSettings toSettings() {
		return this.settings;
	}

	private AbstractBlockSettingsDuckInterface ducked() {
		return (AbstractBlockSettingsDuckInterface) this.settings;
	}

	public BlockSettingsModifiers translucent() {
		this.ducked().setTranslucent(true);
		return this;
	}

	public BlockSettingsModifiers notTranslucent() {
		this.ducked().setNotTranslucent(true);
		return this;
	}

	public BlockSettingsModifiers invisibleSides() {
		this.ducked().setInvisibleSides(true);
		return this;
	}

	public static boolean always(BlockState state, BlockView world, BlockPos pos) {
		return BlocksAccessor.always(state, world, pos);
	}

	public static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return BlocksAccessor.never(state, world, pos);
	}
}
