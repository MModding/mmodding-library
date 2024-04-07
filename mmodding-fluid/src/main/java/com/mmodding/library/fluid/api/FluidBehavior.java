package com.mmodding.library.fluid.api;

import com.mmodding.library.fluid.api.property.FluidProperty;
import com.mmodding.library.fluid.impl.FluidPropertiesImpl;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

import java.util.Comparator;
import java.util.function.Consumer;

public abstract class FluidBehavior implements FluidLike {

	private final IntProperty flowStages;
	private final int surfacePerStage;
	private final FluidProperties properties;

	protected FluidBehavior(IntProperty flowStages) {
		this(flowStages, properties -> {});
	}

	protected FluidBehavior(IntProperty flowStages, Consumer<FluidProperties> properties) {
		this(flowStages, 1, properties);
	}

	protected FluidBehavior(IntProperty flowStages, int surfacePerStage) {
		this(flowStages, surfacePerStage, properties -> {});
	}

	protected FluidBehavior(IntProperty flowStages, int surfacePerStage, Consumer<FluidProperties> properties) {
		this.flowStages = flowStages;
		this.surfacePerStage = surfacePerStage > 0 ? surfacePerStage : 1;
		FluidProperties fluidProperties = new FluidPropertiesImpl();
		properties.accept(fluidProperties);
		this.properties = fluidProperties;
	}

	public abstract Direction getFlowDirection(BlockState state, World world, BlockPos pos, RandomGenerator random);

	public abstract int getFlowSpeedFactor(BlockState state, World world, BlockPos pos, RandomGenerator random);

	public abstract int getMovementFactor(BlockState state, World world, BlockPos pos, RandomGenerator random);

	// When you want to pass through a flowing fluid, with which strength does it try to get you out
	public abstract int getStrengthFactor(BlockState state, World world, BlockPos pos, RandomGenerator random);

	// When you are in the fluid, how much it want to push you to the bottom
	public abstract int getPressureFactor(BlockState state, World world, BlockPos pos, RandomGenerator random);

	public abstract boolean canBoatsBePlacedUpon(BlockState state, World world, BlockPos pos, RandomGenerator random);

	public abstract int randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random);

	public final int getMaxFlowStage() {
		return this.flowStages.stream().map(Property.Value::value).max(Comparator.comparingInt(i -> i)).orElseThrow();
	}

	public final int getSurfacePerStage() {
		return this.surfacePerStage;
	}

	public final <T> T getFluidProperty(FluidProperty<T> fluidProperty) {
		return ((FluidPropertiesImpl) this.properties).getFluidProperty(fluidProperty);
	}

	final IntProperty getProperty() {
		return this.flowStages;
	}

	@Override
	public Type getType() {
		return Type.MODDED;
	}

	@ApiStatus.NonExtendable
	public interface FluidProperties {

		<T> void withFluidProperty(FluidProperty<T> fluidProperty);

		<T> void withFluidProperty(FluidProperty<T> fluidProperty, T value);
	}
}
