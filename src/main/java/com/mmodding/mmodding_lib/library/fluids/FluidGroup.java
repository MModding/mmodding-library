package com.mmodding.mmodding_lib.library.fluids;

import com.mmodding.mmodding_lib.library.blocks.CustomFluidBlock;
import com.mmodding.mmodding_lib.library.fluids.buckets.CustomBucketItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class FluidGroup {

	private final CustomFluid still;
	private final CustomFluid flowing;
	private final CustomFluidBlock block;
	private final CustomBucketItem bucket;

	public FluidGroup(Function<Boolean, CustomFluid> factory, AbstractBlock.Settings settings) {
		this(factory, settings, new AdvancedItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	}

	public FluidGroup(Function<Boolean, CustomFluid> factory, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
		this.still = factory.apply(true);
		this.flowing = factory.apply(false);
		this.block = new CustomFluidBlock(
			this.still,
			blockSettings,
			this.still.getDefaultState().with(FlowableFluid.FALLING, Boolean.FALSE),
			value -> this.flowing.getDefaultState().with(FlowableFluid.LEVEL, value == 8 ? 8 : 8 - value).with(FlowableFluid.FALLING, value == 8 ? Boolean.TRUE : Boolean.FALSE)
		);
		this.bucket = itemSettings != null ? new CustomBucketItem(this.still, itemSettings) : null;
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
