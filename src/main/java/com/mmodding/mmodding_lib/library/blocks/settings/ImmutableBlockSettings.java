package com.mmodding.mmodding_lib.library.blocks.settings;

import com.mmodding.mmodding_lib.mixin.accessors.AbstractBlockAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.AbstractBlockSettingsAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.base.api.util.TriState;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ImmutableBlockSettings extends AdvancedBlockSettings {

	protected ImmutableBlockSettings(Material material, Function<BlockState, MapColor> mapColorProvider) {
		super(material, mapColorProvider);
	}

	protected ImmutableBlockSettings(Material material, MapColor mapColor) {
		super(material, mapColor);
	}

	protected ImmutableBlockSettings(AbstractBlock.Settings settings) {
		super(((AbstractBlockSettingsAccessor) settings).getMaterial(), ((AbstractBlockSettingsAccessor) settings).getMapColorProvider());

		AbstractBlockSettingsAccessor accessor = (AbstractBlockSettingsAccessor) settings;

		super.material(accessor.getMaterial());
		super.hardness(accessor.getHardness());
		super.resistance(accessor.getResistance());
		super.collidable(accessor.getCollidable());
		super.ticksRandomly(accessor.getRandomTicks());
		super.luminance(accessor.getLuminance());
		super.mapColorProvider(accessor.getMapColorProvider());
		super.sounds(accessor.getSoundGroup());
		super.slipperiness(accessor.getSlipperiness());
		super.velocityMultiplier(accessor.getVelocityMultiplier());
		super.dynamicBounds(accessor.getDynamicBounds());
		super.opaque(accessor.getOpaque());
		super.air(accessor.getIsAir());
		super.requiresTool(accessor.isToolRequired());
		super.jumpVelocityMultiplier(accessor.getJumpVelocityMultiplier());
		super.drops(accessor.getLootTableId());
		super.allowsSpawning(accessor.getAllowsSpawningPredicate());
		super.solidBlock(accessor.getSolidBlockPredicate());
		super.suffocates(accessor.getSuffocationPredicate());
		super.blockVision(accessor.getBlockVisionPredicate());
		super.postProcess(accessor.getPostProcessPredicate());
		super.emissiveLighting(accessor.getEmissiveLightingPredicate());

		this.ducked().mmodding_lib$setTranslucent(this.ducked(settings).mmodding_lib$getTranslucent());
		this.ducked().mmodding_lib$setInvisibleSides(this.ducked(settings).mmodding_lib$getInvisibleSides());
		this.ducked().mmodding_lib$setInnerVelocityMultiplier(this.ducked(settings).mmodding_lib$getInnerVelocityMultiplier());
	}

	public static ImmutableBlockSettings of(Material material) {
		return ImmutableBlockSettings.of(material, material.getColor());
	}

	public static ImmutableBlockSettings of(Material material, Function<BlockState, MapColor> mapColorProvider) {
		return new ImmutableBlockSettings(material, mapColorProvider);
	}

	public static ImmutableBlockSettings of(Material material, MapColor color) {
		return new ImmutableBlockSettings(material, color);
	}

	public static ImmutableBlockSettings of(Material material, DyeColor color) {
		return new ImmutableBlockSettings(material, color.getMapColor());
	}

	public static ImmutableBlockSettings copyOf(AbstractBlock block) {
		return new ImmutableBlockSettings(((AbstractBlockAccessor) block).getSettings());
	}

	public static ImmutableBlockSettings copyOf(AbstractBlock.Settings settings) {
		return new ImmutableBlockSettings(settings);
	}

	@Override
	public ImmutableBlockSettings translucent(TriState triState) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.translucent(triState);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings invisibleSides() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.invisibleSides();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings noCollision() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.noCollision();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings nonOpaque() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.nonOpaque();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings slipperiness(float slipperiness) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.slipperiness(slipperiness);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings velocityMultiplier(float velocityMultiplier) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.velocityMultiplier(velocityMultiplier);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.jumpVelocityMultiplier(jumpVelocityMultiplier);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public AdvancedBlockSettings innerVelocityMultiplier(float innerVelocityMultiplier) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.innerVelocityMultiplier(innerVelocityMultiplier);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings sounds(BlockSoundGroup soundGroup) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.sounds(soundGroup);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings luminance(ToIntFunction<BlockState> luminance) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.luminance(luminance);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings strength(float hardness, float resistance) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.strength(hardness, resistance);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings breakInstantly() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.breakInstantly();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings strength(float strength) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.strength(strength);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings ticksRandomly() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.ticksRandomly();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings dynamicBounds() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.dynamicBounds();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings dropsNothing() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.dropsNothing();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings dropsLike(Block source) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.dropsLike(source);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings air() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.air();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.allowsSpawning(predicate);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.solidBlock(predicate);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.suffocates(predicate);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.blockVision(predicate);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.postProcess(predicate);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.emissiveLighting(predicate);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings requiresTool() {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.requiresTool();
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings mapColor(MapColor color) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.mapColor(color);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings hardness(float hardness) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.hardness(hardness);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings resistance(float resistance) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.resistance(resistance);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings material(Material material) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.material(material);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings collidable(boolean collidable) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.collidable(collidable);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings opaque(boolean opaque) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.opaque(opaque);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings ticksRandomly(boolean ticksRandomly) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.ticksRandomly(ticksRandomly);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings dynamicBounds(boolean dynamicBounds) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.dynamicBounds(dynamicBounds);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings requiresTool(boolean requiresTool) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.requiresTool(requiresTool);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings air(boolean isAir) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.air(isAir);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings luminance(int luminance) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.luminance(luminance);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings drops(Identifier dropTableId) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.drops(dropTableId);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings mapColor(DyeColor color) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.mapColor(color);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings mapColorProvider(Function<BlockState, MapColor> mapColorProvider) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.mapColorProvider(mapColorProvider);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings offsetType(AbstractBlock.OffsetType offset) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.offsetType(offset);
		return ImmutableBlockSettings.copyOf(settings);
	}

	@Override
	public ImmutableBlockSettings offsetType(Function<BlockState, AbstractBlock.OffsetType> function) {
		AdvancedBlockSettings settings = AdvancedBlockSettings.copyOf(this);
		settings.offsetType(function);
		return ImmutableBlockSettings.copyOf(settings);
	}
}
