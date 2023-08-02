package com.mmodding.mmodding_lib.library.blocks.interactions.data;

import com.mmodding.mmodding_lib.mixin.accessors.FallingBlockEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FallingBlockInteractionData {

	private final World world;
	private final BlockPos originPos;
	private final BlockPos interactPos;
	private final BlockState fallingBlockState;
	private final BlockState currentBlockState;
	private final boolean destroyedOnLanding;
	private final boolean hurtEntities;
	private final int fallHurtMax;
	private final float fallHurtMultiplier;
	private final float fallDistance;

	private FallingBlockInteractionData(World world, BlockPos originPos, BlockPos interactPos, BlockState fallingBlockState, BlockState currentBlockState, boolean destroyedOnLanding, boolean hurtEntities, int fallHurtMax, float fallHurtMultiplier, float fallDistance) {
		this.world = world;
		this.originPos = originPos;
		this.interactPos = interactPos;
		this.fallingBlockState = fallingBlockState;
		this.currentBlockState = currentBlockState;
		this.destroyedOnLanding = destroyedOnLanding;
		this.hurtEntities = hurtEntities;
		this.fallHurtMax = fallHurtMax;
		this.fallHurtMultiplier = fallHurtMultiplier;
		this.fallDistance = fallDistance;
	}

	public static FallingBlockInteractionData of(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentBlockState, FallingBlockEntity fallingBlockEntity) {
		FallingBlockEntityAccessor accessor = (FallingBlockEntityAccessor) fallingBlockEntity;
		return new FallingBlockInteractionData(
			world,
			pos,
			pos.down(),
			fallingBlockState,
			currentBlockState,
			accessor.getDestroyedOnLanding(),
			accessor.getHurtEntities(),
			accessor.getFallHurtMax(),
			accessor.getFallHurtAmount(),
			fallingBlockEntity.fallDistance
		);
	}

	public World getWorld() {
		return this.world;
	}

	public BlockPos getOriginPos() {
		return this.originPos;
	}

	public BlockPos getInteractPos() {
		return this.interactPos;
	}

	public BlockState getFallingBlockState() {
		return this.fallingBlockState;
	}

	public BlockState getCurrentBlockState() {
		return this.currentBlockState;
	}

	public boolean isDestroyedOnLanding() {
		return this.destroyedOnLanding;
	}

	public boolean doesHurtEntities() {
		return this.hurtEntities;
	}

	public int getMaxFallHurtAmount() {
		return this.fallHurtMax;
	}

	public float getFallHurtMultiplier() {
		return this.fallHurtMultiplier;
	}

	public float getFallDistance() {
		return this.fallDistance;
	}

	public float getFallHurtAmount() {
		return Math.min(MathHelper.floor(MathHelper.ceil((this.getFallDistance() - 1.0f) * this.getFallHurtMultiplier())), this.getMaxFallHurtAmount());
	}
}
