package com.mmodding.library.woodset.impl;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.api.WoodSet;
import com.mmodding.library.woodset.api.WoodSetBuilder;
import com.mmodding.library.woodset.api.WoodSetSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class WoodSetBuilderImpl implements WoodSetBuilder {

	private final String namespace;
	private final String name;
	private final WoodTypeBuilder woodTypeBuilder;
	private final BlockSetTypeBuilder setTypeBuilder;

	private String logName = "log";
	private String woodName = "wood";
	private SoundType woodSoundType = SoundType.WOOD;
	private BlockFactory<? extends AdvancedLeavesBlock> leavesFactory = properties -> new AdvancedLeavesBlock(0.01f, Color.rgb(-12012264), properties);
	private SoundType leavesSoundType = SoundType.GRASS;
	private TreeGrower grower = TreeGrower.OAK;
	private SoundType saplingSoundType = SoundType.GRASS;
	private WoodSet.BoatFactory boatFactory = EntityType::boatFactory;
	private WoodSet.ChestBoatFactory chestBoatFactory = EntityType::chestBoatFactory;
	private AutoMapper<BlockBehaviour.Properties> patch = AutoMapper.identity();
	private WoodSetSettings settings = WoodSetSettings.DEFAULT;

	public WoodSetBuilderImpl(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		this.namespace = namespace;
		this.name = name;
		this.woodTypeBuilder = woodTypeBuilder;
		this.setTypeBuilder = setTypeBuilder;
	}

	@Override
	public WoodSetBuilder withLogName(String log) {
		this.logName = log;
		return this;
	}

	@Override
	public WoodSetBuilder withWoodName(String wood) {
		this.woodName = wood;
		return this;
	}

	@Override
	public WoodSetBuilder withWoodSounds(SoundType soundType) {
		this.woodSoundType = soundType;
		return this;
	}

	@Override
	public WoodSetBuilder withLeavesFactory(BlockFactory<? extends AdvancedLeavesBlock> leavesFactory) {
		this.leavesFactory = leavesFactory;
		return this;
	}

	@Override
	public WoodSetBuilder withLeavesSounds(SoundType soundType) {
		this.leavesSoundType = soundType;
		return this;
	}

	@Override
	public WoodSetBuilder withTreeGrower(TreeGrower grower) {
		this.grower = grower;
		return this;
	}

	@Override
	public WoodSetBuilder withSaplingSounds(SoundType soundType) {
		this.saplingSoundType = soundType;
		return this;
	}

	@Override
	public WoodSetBuilder withBoatFactory(WoodSet.BoatFactory boatFactory) {
		this.boatFactory = boatFactory;
		return this;
	}

	@Override
	public WoodSetBuilder withChestBoatFactory(WoodSet.ChestBoatFactory chestBoatFactory) {
		this.chestBoatFactory = chestBoatFactory;
		return this;
	}

	@Override
	public WoodSetBuilder withOverallPatch(AutoMapper<BlockBehaviour.Properties> patch) {
		this.patch = patch;
		return this;
	}

	@Override
	public WoodSetBuilder withSettings(WoodSetSettings settings) {
		this.settings = settings;
		return this;
	}

	@Override
	public WoodSet buildAndRegister() {
		return new WoodSetImpl(
			this.namespace,
			this.name,
			this.woodTypeBuilder,
			this.setTypeBuilder,
			this.logName,
			this.woodName,
			this.woodSoundType,
			this.leavesFactory,
			this.leavesSoundType,
			this.grower,
			this.saplingSoundType,
			this.boatFactory,
			this.chestBoatFactory,
			this.patch,
			this.settings
		);
	}
}
