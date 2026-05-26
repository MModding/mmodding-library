package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.block.api.MModdingBlock;
import net.minecraft.core.Holder;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements BlockWithItem, MModdingBlock {

	@Shadow
	@Nullable
	private Item item;

	@Shadow
	@Final
	private Holder.Reference<Block> builtInRegistryHolder;

	@Override
	@SuppressWarnings({"unchecked", "DataFlowIssue"})
	public <T extends Block> T registerItem(@NotNull BiFunction<T, Item.Properties, Item> factory, Item.@NotNull Properties properties, @NotNull Function<Item, Item> tweaker) {
		if (this.item == null) {
			BlockItemId blockItemId = BlockItemId.create(this.builtInRegistryHolder.key().identifier(), this.builtInRegistryHolder.key().identifier());
			this.item = tweaker.apply(Items.registerBlock(blockItemId, (Block) (Object) this, (BiFunction<Block, Item.Properties, Item>) factory, properties));
			return (T) (Object) this;
		}
		else {
			throw new RuntimeException("Tried to link a new item to a block that already had one");
		}
	}
}
