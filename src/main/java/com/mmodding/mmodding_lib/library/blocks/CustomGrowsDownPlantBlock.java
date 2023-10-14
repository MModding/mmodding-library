package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import com.mmodding.mmodding_lib.library.client.render.layer.RenderLayerOperations;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

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
		this(settings, tickWater, growthChance, growLength, chooseStemState, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomGrowsDownPlantBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState, boolean hasItem, Item.Settings itemSettings) {
		this.head = new Head(settings, this, tickWater, growthChance, growLength, chooseStemState);
		this.body = new Body(settings, this, tickWater);
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

	@ClientOnly
	public void cutout() {
		RenderLayerOperations.setCutout(this.head);
		RenderLayerOperations.setCutout(this.body);
	}

	@ClientOnly
	public void translucent() {
		RenderLayerOperations.setTranslucent(this.head);
		RenderLayerOperations.setTranslucent(this.body);
	}

	@ClientOnly
	public void transparent() {
		RenderLayerOperations.setTransparent(this.head);
		RenderLayerOperations.setTransparent(this.body);
	}

	public static class Head extends AbstractPlantStemBlock {

		private final CustomGrowsDownPlantBlock plant;
		private final int growLength;
		private final Predicate<BlockState> chooseStemState;

		private Head(AbstractBlock.Settings settings, CustomGrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
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

		public Body(Settings settings, CustomGrowsDownPlantBlock plant, boolean tickWater) {
			super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater);
			this.plant = plant;
		}

		@Override
		protected AbstractPlantStemBlock getStem() {
			return this.plant.head;
		}
	}
}
