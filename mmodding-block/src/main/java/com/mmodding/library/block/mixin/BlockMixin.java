package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.block.impl.BlockWithItemImpl;
import com.mmodding.library.core.api.registry.Registrable;
import com.mmodding.library.core.api.registry.RegistrationStatus;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements BlockWithItem, Registrable<Block>, BlockWithItemImpl.Getter {

	@Unique
	private static final Set<Block> BUILTIN_BLOCKS = Arrays.stream(Blocks.class.getDeclaredFields())
		.filter(field -> field.getDeclaringClass().equals(Block.class))
		.map(field -> {
			try {
				return (Block) field.get(Blocks.class);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		})
		.collect(Collectors.toSet());

	@Unique
	private Item item = null;

	@Unique
	private RegistrationStatus status = RegistrationStatus.UNREGISTERED;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void cancelBuiltinBlocks(AbstractBlock.Settings settings, CallbackInfo ci) {
		if (BlockMixin.BUILTIN_BLOCKS.contains(this.as())) {
			this.status = RegistrationStatus.CANCELLED;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Block> T withItem(Item.@NotNull Settings settings, @NotNull BiFunction<T, Item.Settings, Item> factory,  @NotNull Function<Item, Item> tweaker) {
		if (this.item == null) {
			this.item = tweaker.apply(factory.apply((T) this.as(), settings));
			return (T) this.as();
		}
		else {
			throw new RuntimeException("Tried to link a new item to a block that already had one");
		}
	}

	@Override
	public RegistrationStatus getRegistrationStatus() {
		return this.status;
	}

	@Override
	public void postRegister(Identifier identifier, Block block) {
		this.status = RegistrationStatus.REGISTERED;
		if (this.item != null) {
			Registry.register(Registries.ITEM, identifier, this.item);
		}
	}

	@Override
	public Block as() {
		return (Block) (Object) this;
	}

	@Override
	public Item mmodding$getItem() {
		return this.item;
	}
}
