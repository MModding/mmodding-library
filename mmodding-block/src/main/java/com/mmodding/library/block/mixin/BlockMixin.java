package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.block.api.MModdingBlock;
import com.mmodding.library.block.impl.BlockWithItemImpl;
import com.mmodding.library.core.api.registry.StaticElement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements BlockWithItem, MModdingBlock, StaticElement<Block>, BlockWithItemImpl.Getter {

	@Unique
	private Item item = null;

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Block> T withItem(Item.@NotNull Settings settings, @NotNull BiFunction<T, Item.Settings, Item> factory,  @NotNull Function<Item, Item> tweaker) {
		if (this.item == null) {
			this.item = tweaker.apply(factory.apply((T) this.mmodding$as(), settings));
			return (T) this.mmodding$as();
		}
		else {
			throw new RuntimeException("Tried to link a new item to a block that already had one");
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, @Nullable ItemPlacementContext context) {
		return context != null ? state.canReplace(context) : state.isReplaceable();
	}

	@Override
	public Registry<Block> mmodding$getRegistry() {
		return Registries.BLOCK;
	}

	@Override
	public Block mmodding$as() {
		return (Block) (Object) this;
	}

	@Override
	public Item mmodding$getItem() {
		return this.item;
	}
}
