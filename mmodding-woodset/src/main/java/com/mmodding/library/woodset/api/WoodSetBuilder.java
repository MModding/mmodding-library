package com.mmodding.library.woodset.api;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.function.AutoMapper;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * A builder for {@link WoodSet} objects.
 * Any missing setting will lead to the use default values.
 */
public interface WoodSetBuilder {

	/**
	 * Creates a new {@link WoodSetBuilder}.
	 * @param namespace the namespace of the mod
	 * @param name the name of the wood set
	 * @param woodTypeBuilder the wood type builder
	 * @param setTypeBuilder the set type builder
	 * @return the newly created builder
	 */
	static WoodSetBuilder create(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return null;
	}

	/**
	 * Creates a new {@link WoodSetBuilder} with default Nether parameters.
	 * @param namespace the namespace of the mod
	 * @param name the name of the wood set
	 * @param woodTypeBuilder the wood type builder
	 * @param setTypeBuilder the set type builder
	 * @return the newly created builder
	 */
	static WoodSetBuilder createNether(String namespace, String name, WoodTypeBuilder woodTypeBuilder, BlockSetTypeBuilder setTypeBuilder) {
		return WoodSetBuilder.create(namespace, name, woodTypeBuilder, setTypeBuilder)
			.withLogName("stem")
			.withWoodName("hyphae")
			.doesNotBurn()
			.withWoodSounds(SoundType.NETHER_WOOD)
			.withUntintedLeaves(0.0f, ParticleTypes.SOUL_FIRE_FLAME);
	}

	/**
	 * Sets a string which replaces "log" in the log block identifier.
	 * <br>Defaults to <code>"log"</code>.
	 * @param log the log name
	 * @return the builder
	 */
	WoodSetBuilder withLogName(String log);

	/**
	 * Sets a string which replaces "wood" in the wood block identifier.
	 * <br>Defaults to <code>"wood"</code>.
	 * @param wood the wood name
	 * @return the builder
	 */
	WoodSetBuilder withWoodName(String wood);

	/**
	 * Indicates that the wood set entries are not burnable.
	 * @return the builder
	 */
	WoodSetBuilder doesNotBurn();

	/**
	 * Sets a {@link SoundType} for standard wood objects.
	 * <br>Defaults to {@link SoundType#WOOD}.
	 * @param soundType the sound type
	 * @return the builder
	 */
	WoodSetBuilder withWoodSounds(SoundType soundType);

	/**
	 * Sets a {@link WoodSet.LogDisplay}, indicating which model should be used for the log.
	 * <br>Defaults to {@link WoodSet.LogDisplay#WITH_HORIZONTAL}.
	 * @param logDisplay the log display
	 * @return the builder
	 */
	WoodSetBuilder withLogDisplay(WoodSet.LogDisplay logDisplay);

	/**
	 * Sets tinted leaves parameters.
	 * @param leafParticleChance the leaf particle chance
	 * @param itemTintColor the item tint color
	 * @return the builder
	 */
	default WoodSetBuilder withTintedLeaves(float leafParticleChance, Color itemTintColor) {
		return this.withLeavesFactory(properties -> new AdvancedLeavesBlock(leafParticleChance, itemTintColor, properties));
	}

	/**
	 * Sets untinted leaves parameters.
	 * @param leafParticleChance the leaf particle chance
	 * @param leafParticle the item tint color
	 * @return the builder
	 */
	default WoodSetBuilder withUntintedLeaves(float leafParticleChance, ParticleOptions leafParticle) {
		return this.withLeavesFactory(properties -> new AdvancedLeavesBlock(leafParticleChance, leafParticle, properties));
	}

	/**
	 * Sets a {@link BlockFactory} for the leaves block. It allows advanced configuration of the leaves, if necessary.
	 * <br>Defaults to the factory used by {@link Blocks#OAK_LEAVES}.
	 * @param leavesFactory the leaves factory
	 * @return the builder
	 */
	WoodSetBuilder withLeavesFactory(BlockFactory<? extends AdvancedLeavesBlock> leavesFactory);

	/**
	 * Sets a {@link SoundType} for the leaves.
	 * <br>Defaults to {@link SoundType#GRASS}.
	 * @param soundType the sound type
	 * @return the builder
	 */
	WoodSetBuilder withLeavesSounds(SoundType soundType);

	/**
	 * Sets a {@link TreeGrower} for the sapling.
	 * <br>Defaults to {@link TreeGrower#OAK}.
	 * @param grower the tree grower
	 * @return the builder
	 */
	WoodSetBuilder withTreeGrower(TreeGrower grower);

	/**
	 * Sets a {@link SoundType} for the sapling.
	 * <br>Defaults to {@link SoundType#GRASS}.
	 * @param soundType the sound type
	 * @return the builder
	 */
	WoodSetBuilder withSaplingSounds(SoundType soundType);

	/**
	 * Sets a {@link WoodSet.BoatFactory} to use when creating the boat entity type.
	 * <br>Defaults to the factory used by {@link EntityType#OAK_BOAT}.
	 * @param boatFactory the boat factory
	 * @return the builder
	 */
	WoodSetBuilder withBoatFactory(WoodSet.BoatFactory boatFactory);

	/**
	 * Sets a {@link WoodSet.ChestBoatFactory} to use when creating the chest boat entity type.
	 * <br>Defaults to the factory used by {@link EntityType#CHERRY_BOAT}.
	 * @param chestBoatFactory the chest boat factory
	 * @return the builder
	 */
	WoodSetBuilder withChestBoatFactory(WoodSet.ChestBoatFactory chestBoatFactory);

	/**
	 * Sets a patch which applies to every block properties of the wood set.
	 * <br>Defaults to {@link AutoMapper#identity()}.
	 * @param patch the patch
	 * @return the builder
	 */
	WoodSetBuilder withOverallPatch(AutoMapper<BlockBehaviour.Properties> patch);

	/**
	 * Builds and registers the {@link WoodSet}.
	 * Do not forget to classload the woodset at mod initialization!
	 * @return the newly built wood set
	 */
	WoodSet buildAndRegister();
}
