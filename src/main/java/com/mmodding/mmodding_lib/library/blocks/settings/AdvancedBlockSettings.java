package com.mmodding.mmodding_lib.library.blocks.settings;

import com.mmodding.mmodding_lib.ducks.AbstractBlockSettingsDuckInterface;
import com.mmodding.mmodding_lib.mixin.accessors.AbstractBlockAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.BlocksAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class AdvancedBlockSettings extends FabricBlockSettings {

	protected AdvancedBlockSettings(Material material, MapColor mapColor) {
		super(material, mapColor);
	}

	protected AdvancedBlockSettings(AbstractBlock.Settings settings) {
		super(settings);
		this.ducked().mmodding_lib$setInnerVelocityMultiplier(this.ducked(settings).mmodding_lib$getInnerVelocityMultiplier());
		this.ducked().mmodding_lib$setTranslucent(this.ducked(settings).mmodding_lib$getTranslucent());
		this.ducked().mmodding_lib$setInvisibleSides(this.ducked(settings).mmodding_lib$getInvisibleSides());
	}

	public static AdvancedBlockSettings of(Material material) {
		return AdvancedBlockSettings.of(material, material.getColor());
	}

	public static AdvancedBlockSettings of(Material material, MapColor color) {
		return new AdvancedBlockSettings(material, color);
	}

	public static AdvancedBlockSettings of(Material material, DyeColor color) {
		return new AdvancedBlockSettings(material, color.getMapColor());
	}

	public static AdvancedBlockSettings copyOf(AbstractBlock block) {
		return new AdvancedBlockSettings(((AbstractBlockAccessor) block).getSettings());
	}

	public static AdvancedBlockSettings copyOf(AbstractBlock.Settings settings) {
		return new AdvancedBlockSettings(settings);
	}

	public AdvancedBlockSettings translucent(TriState triState) {
		this.ducked().mmodding_lib$setTranslucent(triState);
		return this;
	}

	public AdvancedBlockSettings invisibleSides() {
		this.ducked().mmodding_lib$setInvisibleSides(true);
		return this;
	}

	public static boolean always(BlockState state, BlockView world, BlockPos pos) {
		return BlocksAccessor.invokeAlways(state, world, pos);
	}

	public static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return BlocksAccessor.invokeNever(state, world, pos);
	}

	@Override
	public AdvancedBlockSettings noCollision() {
		super.noCollision();
		return this;
	}

	@Override
	public AdvancedBlockSettings nonOpaque() {
		super.nonOpaque();
		return this;
	}

	@Override
	public AdvancedBlockSettings slipperiness(float slipperiness) {
		super.slipperiness(slipperiness);
		return this;
	}

	@Override
	public AdvancedBlockSettings velocityMultiplier(float velocityMultiplier) {
		super.velocityMultiplier(velocityMultiplier);
		return this;
	}

	@Override
	public AdvancedBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
		super.jumpVelocityMultiplier(jumpVelocityMultiplier);
		return this;
	}

	public AdvancedBlockSettings innerVelocityMultiplier(float innerVelocityMultiplier) {
		this.ducked().mmodding_lib$setInnerVelocityMultiplier(innerVelocityMultiplier);
		return this;
	}

	@Override
	public AdvancedBlockSettings sounds(BlockSoundGroup soundGroup) {
		super.sounds(soundGroup);
		return this;
	}

	@Override
	public AdvancedBlockSettings luminance(ToIntFunction<BlockState> luminance) {
		super.luminance(luminance);
		return this;
	}

	@Override
	public AdvancedBlockSettings strength(float hardness, float resistance) {
		super.strength(hardness, resistance);
		return this;
	}

	@Override
	public AdvancedBlockSettings breakInstantly() {
		super.breakInstantly();
		return this;
	}

	@Override
	public AdvancedBlockSettings strength(float strength) {
		super.strength(strength);
		return this;
	}

	@Override
	public AdvancedBlockSettings ticksRandomly() {
		super.ticksRandomly();
		return this;
	}

	@Override
	public AdvancedBlockSettings dynamicBounds() {
		super.dynamicBounds();
		return this;
	}

	@Override
	public AdvancedBlockSettings dropsNothing() {
		super.dropsNothing();
		return this;
	}

	@Override
	public AdvancedBlockSettings dropsLike(Block source) {
		super.dropsLike(source);
		return this;
	}

	@Override
	public AdvancedBlockSettings air() {
		super.air();
		return this;
	}

	@Override
	public AdvancedBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
		super.allowsSpawning(predicate);
		return this;
	}

	@Override
	public AdvancedBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
		super.solidBlock(predicate);
		return this;
	}

	@Override
	public AdvancedBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
		super.suffocates(predicate);
		return this;
	}

	@Override
	public AdvancedBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
		super.blockVision(predicate);
		return this;
	}

	@Override
	public AdvancedBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
		super.postProcess(predicate);
		return this;
	}

	@Override
	public AdvancedBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
		super.emissiveLighting(predicate);
		return this;
	}

	@Override
	public AdvancedBlockSettings requiresTool() {
		super.requiresTool();
		return this;
	}

	@Override
	public AdvancedBlockSettings mapColor(MapColor color) {
		super.mapColor(color);
		return this;
	}

	@Override
	public AdvancedBlockSettings hardness(float hardness) {
		super.hardness(hardness);
		return this;
	}

	@Override
	public AdvancedBlockSettings resistance(float resistance) {
		super.resistance(resistance);
		return this;
	}

	@Override
	public AdvancedBlockSettings material(Material material) {
		super.material(material);
		return this;
	}

	@Override
	public AdvancedBlockSettings collidable(boolean collidable) {
		super.collidable(collidable);
		return this;
	}

	@Override
	public AdvancedBlockSettings opaque(boolean opaque) {
		super.opaque(opaque);
		return this;
	}

	@Override
	public AdvancedBlockSettings ticksRandomly(boolean ticksRandomly) {
		super.ticksRandomly(ticksRandomly);
		return this;
	}

	@Override
	public AdvancedBlockSettings dynamicBounds(boolean dynamicBounds) {
		super.dynamicBounds(dynamicBounds);
		return this;
	}

	@Override
	public AdvancedBlockSettings requiresTool(boolean requiresTool) {
		super.requiresTool(requiresTool);
		return this;
	}

	@Override
	public AdvancedBlockSettings air(boolean isAir) {
		super.air(isAir);
		return this;
	}

	@Override
	public AdvancedBlockSettings luminance(int luminance) {
		super.luminance(luminance);
		return this;
	}

	@Override
	public AdvancedBlockSettings drops(Identifier dropTableId) {
		super.drops(dropTableId);
		return this;
	}

	@Override
	public AdvancedBlockSettings mapColor(DyeColor color) {
		super.mapColor(color);
		return this;
	}

	@Override
	public AdvancedBlockSettings offsetType(AbstractBlock.OffsetType offset) {
		super.offsetType(offset);
		return this;
	}

	@Override
	public AdvancedBlockSettings offsetType(Function<BlockState, AbstractBlock.OffsetType> function) {
		super.offsetType(function);
		return this;
	}

	AbstractBlockSettingsDuckInterface ducked(AbstractBlock.Settings settings) {
		return (AbstractBlockSettingsDuckInterface) settings;
	}

	AbstractBlockSettingsDuckInterface ducked() {
		return this.ducked(this);
	}
}
