package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.block.api.MModdingBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements BlockWithItem, MModdingBlock {

	@Shadow
	@Nullable
	private Item item;

	@Override
	@SuppressWarnings({"unchecked", "DataFlowIssue"})
	public <T extends Block> T withItem(Item.@NotNull Properties settings, @NotNull BiFunction<T, Item.Properties, Item> factory,  @NotNull Function<Item, Item> tweaker) {
		if (this.item == null) {
			this.item = tweaker.apply(factory.apply((T) (Object) this, settings));
			return (T) (Object) this;
		}
		else {
			throw new RuntimeException("Tried to link a new item to a block that already had one");
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, @Nullable BlockPlaceContext context) {
		return context != null ? state.canBeReplaced(context) : state.canBeReplaced();
	}
}
