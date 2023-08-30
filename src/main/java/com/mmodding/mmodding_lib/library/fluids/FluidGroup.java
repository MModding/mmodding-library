package com.mmodding.mmodding_lib.library.fluids;

import com.mmodding.mmodding_lib.library.blocks.CustomFluidBlock;
import com.mmodding.mmodding_lib.library.items.CustomBucketItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public class FluidGroup {

	private final CustomFluid still;
	private final CustomFluid flowing;
	private final CustomFluidBlock block;
	private final CustomBucketItem bucket;

	public FluidGroup(CustomFluid still, CustomFluid flowable, AbstractBlock.Settings settings) {
		this(still, flowable, settings, new AdvancedItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	}

	public FluidGroup(CustomFluid still, CustomFluid flowing, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
		this.still = still;
		this.flowing = flowing;
		this.block = new CustomFluidBlock(
			this.still,
			blockSettings,
			this.still.getDefaultState().with(FlowableFluid.FALLING, Boolean.FALSE),
			value -> this.flowing.getDefaultState().with(FlowableFluid.LEVEL, value == 8 ? 8 : 8 - value).with(FlowableFluid.FALLING, Boolean.TRUE)
		);
		this.bucket = new CustomBucketItem(this.still, itemSettings);
	}

	public CustomFluid getStill() {
		return this.still;
	}

	public CustomFluid getFlowing() {
		return this.flowing;
	}

	public CustomFluidBlock getBlock() {
		return this.block;
	}

	@Nullable
	public BucketItem getBucket() {
		return this.bucket;
	}

	public void register(Identifier identifier) {
		this.still.register(identifier);
		this.flowing.register(IdentifierUtils.extend("flowing", identifier));
		this.block.register(identifier);
		if (this.bucket != null) {
			this.bucket.register(IdentifierUtils.extend(identifier, "bucket"));
		}
	}
}
