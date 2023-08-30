package com.mmodding.mmodding_lib.library.fluids;

import com.mmodding.mmodding_lib.library.blocks.CustomFluidBlock;
import com.mmodding.mmodding_lib.library.items.CustomBucketItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class FluidGroup {

	private final Identifier identifier;
	private final CustomFluid still;
	private final CustomFluid flowing;
	private final CustomFluidBlock block;
	private final CustomBucketItem bucket;

	public FluidGroup(Identifier identifier, CustomFluid still, CustomFluid flowable, AbstractBlock.Settings settings) {
		this(identifier, still, flowable, settings, new AdvancedItemSettings().recipeRemainder(Items.BUCKET).maxCount(1));
	}

	public FluidGroup(Identifier identifier, CustomFluid still, CustomFluid flowing, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
		this.identifier = identifier;
		this.still = still;
		this.still.register(this.identifier);
		this.flowing = flowing;
		this.flowing.register(IdentifierUtils.extend("flowing", this.identifier));
		this.block = new CustomFluidBlock(this.still, blockSettings);
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

	public void register() {
		this.block.register(this.identifier);
		if (this.bucket != null) {
			this.bucket.register(IdentifierUtils.extend(identifier, "bucket"));
		}
	}
}
