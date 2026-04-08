package com.mmodding.library.fluid.api;

import com.mmodding.library.fluid.api.property.CommonFluidProperties;
import com.mmodding.library.fluid.api.property.FluidProperties;
import com.mmodding.library.fluid.impl.FluidPropertiesImpl;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * An advanced version of {@link UnitedFlowableFluid} which lets you provide other settings to your flowable fluid.
 */
public abstract class AdvancedFlowableFluid extends UnitedFlowableFluid {

	private final FluidProperties properties;

	public AdvancedFlowableFluid(IntProperty levels, boolean still) {
		super(levels, still);
		FluidPropertiesImpl.Builder builder = new FluidPropertiesImpl.Builder();
		this.appendFluidProperties(builder);
		this.properties = builder.build();
	}

	/**
	 * Works in a similar way than {@link UnitedFlowableFluid#appendProperties(StateManager.Builder)}.
	 * Though, in this case, it does not affect the fluid state properties at all, it only provides
	 * fluid properties such as the {@link CommonFluidProperties}.
	 * @param builder the fluid properties builder
	 */
	protected abstract void appendFluidProperties(FluidProperties.Builder builder);

	/**
	 * Handles the collision logic.
	 * @param world the world
	 * @param pos the position
	 * @param direction the direction towards the neighboring position
	 * @param neighborPos the neighboring position
	 */
	public abstract void neighborCollision(World world, BlockPos pos, Direction direction, BlockPos neighborPos);

	/**
	 * Gives read access to the fluid properties.
	 * @return the fluid properties.
	 */
	public final FluidProperties getFluidProperties() {
		return this.properties;
	}

	/**
	 * Indicates what particle should drip from dripstones when this fluid is above.
	 * @return the dripping particle
	 */
	public abstract ParticleEffect getDrippingParticle();
}
