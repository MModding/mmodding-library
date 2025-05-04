package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.client.render.layer.RenderLayerOperations;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomGrowsDownPlantBlock implements BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	private final Head head;
	private final Body body;

	public CustomGrowsDownPlantBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
		this(settings, tickWater, growthChance, growLength, chooseStemState, false);
	}

	public CustomGrowsDownPlantBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState, boolean hasItem) {
		this(settings, tickWater, growthChance, growLength, chooseStemState, hasItem, (ItemGroup) null);
	}

	public CustomGrowsDownPlantBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState, boolean hasItem, ItemGroup itemGroup) {
		this(settings, tickWater, growthChance, growLength, chooseStemState, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomGrowsDownPlantBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState, boolean hasItem, Item.Settings itemSettings) {
		this(Head::new, Body::new, settings, tickWater, growthChance, growLength, chooseStemState, hasItem, itemSettings);
	}

	public CustomGrowsDownPlantBlock(HeadMaker headMaker, BodyMaker bodyMaker, AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState, boolean hasItem, Item.Settings itemSettings) {
		this.head = headMaker.make(settings, this, tickWater, growthChance, growLength, chooseStemState);
		this.body = bodyMaker.make(settings, this, tickWater);
		if (hasItem) this.item = new BlockItem(this.head, itemSettings);
	}

	@Override
	public BlockItem getItem() {
		return this.item;
	}

	public Head getHead() {
		return this.head;
	}

	public Body getBody() {
		return this.body;
	}

	// Returns the same block by default. If you have a fruit with that plant, you should add it in the state's properties
	public BlockState withFruits(BlockState state) {
		return state;
	}

	public void register(Identifier identifier) {
		if (this.isNotRegistered()) {
			RegistrationUtils.registerBlockWithoutItem(IdentifierUtils.extend(identifier, "head"), this.head);
			RegistrationUtils.registerBlockWithoutItem(identifier, this.body);
			if (item != null) RegistrationUtils.registerItem(identifier, this.getItem());
			this.setRegistered();
		}
	}

	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	public void setRegistered() {
		this.registered.set(true);
	}

	@Environment(EnvType.CLIENT)
	public void cutout() {
		RenderLayerOperations.setCutout(this.head);
		RenderLayerOperations.setCutout(this.body);
	}

	@Environment(EnvType.CLIENT)
	public void translucent() {
		RenderLayerOperations.setTranslucent(this.head);
		RenderLayerOperations.setTranslucent(this.body);
	}

	@Environment(EnvType.CLIENT)
	public void transparent() {
		RenderLayerOperations.setTransparent(this.head);
		RenderLayerOperations.setTransparent(this.body);
	}

	public static class Head extends AbstractPlantStemBlock {

		private final CustomGrowsDownPlantBlock plant;
		private final int growLength;
		private final Predicate<BlockState> chooseStemState;

		protected Head(AbstractBlock.Settings settings, CustomGrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
			super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater, growthChance);
			this.plant = plant;
			this.growLength = growLength;
			this.chooseStemState = chooseStemState;
		}

		@Override
		protected int getGrowthLength(RandomGenerator random) {
			return this.growLength;
		}

		@Override
		protected boolean chooseStemState(BlockState state) {
			return this.chooseStemState.test(state);
		}

		@Override
		protected Block getPlant() {
			return this.plant.body;
		}
	}

	public static class Body extends AbstractPlantBlock {

		private final CustomGrowsDownPlantBlock plant;

		protected Body(Settings settings, CustomGrowsDownPlantBlock plant, boolean tickWater) {
			super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater);
			this.plant = plant;
		}

		@Override
		protected AbstractPlantStemBlock getStem() {
			return this.plant.head;
		}
	}

	public interface HeadMaker {

		Head make(AbstractBlock.Settings settings, CustomGrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState);
	}

	public interface BodyMaker {

		Body make(AbstractBlock.Settings settings, CustomGrowsDownPlantBlock plant, boolean tickWater);
	}

	/**
	 * Utility Interface helping about Growing Down Plants having Fruits.
	 */
	public interface WithFruits {

		BooleanProperty getFruitsProperty();

		Item getFruitItem(BlockState state, World world, BlockPos pos);

		int getPickingCount(BlockState state, World world, BlockPos pos);

		default ActionResult pickBerries(BlockState state, World world, BlockPos pos) {
			if (state.get(this.getFruitsProperty())) {
				Block.dropStack(world, pos, new ItemStack(this.getFruitItem(state, world, pos), this.getPickingCount(state, world, pos)));
				world.playSound(
					null,
					pos,
					SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES,
					SoundCategory.BLOCKS,
					1.0f,
					MathHelper.nextBetween(world.getRandom(), 0.8f, 1.2f)
				);
				world.setBlockState(pos, state.with(this.getFruitsProperty(), Boolean.FALSE), Block.NOTIFY_LISTENERS);
				return ActionResult.success(world.isClient());
			} else {
				return ActionResult.PASS;
			}
		}

		default boolean hasBerries(BlockState state) {
			return state.contains(this.getFruitsProperty()) && state.get(this.getFruitsProperty());
		}
	}
}
