package com.mmodding.library.woodset.impl;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.woodset.api.WoodSet;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class WoodSetImpl extends PlankSetImpl implements WoodSet {

	private final Block log;
	private final Block wood;
	private final Block strippedLog;
	private final Block strippedWood;
	private final Block leaves;
	private final Block sapling;

	public WoodSetImpl(String namespace, String name, String log, String wood, BlockFactory<? extends AdvancedLeavesBlock> leavesFactory, TreeGrower grower, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory, AutoMapper<BlockBehaviour.Properties> patch) {
		super(namespace, name, woodTypeBuilder, setTypeBuilder, boatFactory, chestBoatFactory, patch);
		this.log = this.registerBlock("_" + log, RotatedPillarBlock::new, patch).registerItem();
		this.wood = this.registerBlock("_" + wood, RotatedPillarBlock::new, patch).registerItem();
		this.strippedLog = this.registerBlock("stripped_", "_" + log, RotatedPillarBlock::new, patch).registerItem();
		this.strippedWood = this.registerBlock("stripped_", "_" + wood, RotatedPillarBlock::new, patch).registerItem();
		StrippableBlockRegistry.register(this.log, this.strippedLog);
		StrippableBlockRegistry.register(this.wood, this.strippedWood);
		this.leaves = this.registerBlock("_leaves", leavesFactory, patch).registerItem();
		this.sapling = this.registerBlock("_sapling", properties -> new SaplingBlock(grower, properties), patch).registerItem();
	}

	@Override
	public Block getLog() {
		return this.log;
	}

	@Override
	public Block getWood() {
		return this.wood;
	}

	@Override
	public Block getStrippedLog() {
		return this.strippedLog;
	}

	@Override
	public Block getStrippedWood() {
		return this.strippedWood;
	}

	@Override
	public Block getLeaves() {
		return this.leaves;
	}

	@Override
	public Block getSapling() {
		return this.sapling;
	}
}
