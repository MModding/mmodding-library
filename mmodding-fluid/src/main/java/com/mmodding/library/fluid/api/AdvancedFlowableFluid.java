package com.mmodding.library.fluid.api;

import com.mmodding.library.fluid.api.property.CommonFluidProperties;
import com.mmodding.library.fluid.api.property.FluidProperties;
import com.mmodding.library.fluid.impl.FluidPropertiesImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * An advanced version of {@link UnitedFlowableFluid} which lets you provide other settings to your flowable fluid.
 */
public abstract class AdvancedFlowableFluid extends UnitedFlowableFluid {

	private final FluidProperties properties;

	public AdvancedFlowableFluid(IntegerProperty levels, boolean source) {
		super(levels, source);
		FluidPropertiesImpl.Builder builder = new FluidPropertiesImpl.Builder();
		this.createFluidProperties(builder);
		this.properties = builder.build();
	}

	/**
	 * Works in a similar way than {@link UnitedFlowableFluid#createFluidStateDefinition(StateDefinition.Builder)}.
	 * Though, in this case, it does not affect the fluid state properties at all, it only provides
	 * fluid properties such as the {@link CommonFluidProperties}.
	 * @param builder the fluid properties builder
	 */
	protected abstract void createFluidProperties(FluidProperties.Builder builder);

	/**
	 * Handles the collision logic.
	 * @param world the world
	 * @param pos the position
	 * @param direction the direction towards the neighboring position
	 * @param neighborPos the neighboring position
	 */
	public abstract void neighborCollision(Level world, BlockPos pos, Direction direction, BlockPos neighborPos);

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
	public abstract ParticleOptions getDrippingParticle();
}
