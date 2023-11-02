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
		this.ducked().mmodding_lib$setTranslucent(true);
		return this;
	}

	public BlockSettingsModifiers notTranslucent() {
		this.ducked().mmodding_lib$setNotTranslucent(true);
		return this;
	}

	public BlockSettingsModifiers invisibleSides() {
		this.ducked().mmodding_lib$setInvisibleSides(true);
		return this;
	}

	public static boolean always(BlockState state, BlockView world, BlockPos pos) {
		return BlocksAccessor.invokeAlways(state, world, pos);
	}

	public static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return BlocksAccessor.invokeNever(state, world, pos);
	}
}
