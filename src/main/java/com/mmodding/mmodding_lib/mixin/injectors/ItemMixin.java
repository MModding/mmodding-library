package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.interface_injections.ItemGlintPack;
import com.mmodding.mmodding_lib.library.helpers.CustomSquaredPortalAreaHelper;
import com.mmodding.mmodding_lib.library.items.settings.*;
import com.mmodding.mmodding_lib.library.portals.CustomPortalKey;
import com.mmodding.mmodding_lib.library.portals.Ignition;
import com.mmodding.mmodding_lib.library.portals.squared.AbstractSquaredPortal;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemGlintPack, Self<Item> {

	@Shadow
	public abstract boolean isFood();

	@Inject(method = "hasGlint", at = @At("TAIL"), cancellable = true)
	private void hasGlint(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (AdvancedItemSettings.GLINT.get(this.getObject())) {
			cir.setReturnValue(true);
		}
	}

    @Inject(method = "use", at = @At("HEAD"))
	private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemUse itemUse = AdvancedItemSettings.ITEM_USE.get(this.getObject());
		if (itemUse != null) {
			itemUse.apply(world, user, hand);
		}
	}

	@Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
	private void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		ItemFinishUsing itemFinishUsing = AdvancedItemSettings.ITEM_FINISH_USING.get(this.getObject());
		if (itemFinishUsing != null) {
			cir.setReturnValue(itemFinishUsing.apply(this.isFood() ? user.eatFood(world, stack) : stack, world, user));
		}
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {

		if (this instanceof CustomPortalKey key) {

			BlockPos pos = context.getBlockPos().offset(context.getSide());

			for (Identifier identifier : MModdingGlobalMaps.getAllCustomSquaredPortalKeys()) {
				AbstractSquaredPortal squaredPortal = MModdingGlobalMaps.getAbstractSquaredPortal(identifier);

				Ignition.Key keyIgnition = squaredPortal.getIgnition().toKey();

				if (keyIgnition != null) {

					if (keyIgnition.getKey().getItem() == key.getItem()) {

						Optional<CustomSquaredPortalAreaHelper> optional = squaredPortal.getNewCustomPortal(context.getWorld(), pos, Direction.Axis.X);

						if (optional.isPresent()) {
							optional.get().createPortal();
							context.getWorld().playSound(null, pos, key.getIgniteSound(), SoundCategory.BLOCKS, 1.0f, context.getWorld().getRandom().nextFloat() * 0.4f + 0.8f);
							cir.setReturnValue(ActionResult.SUCCESS);
						}
					}
				}
			}
		}

		ItemUseOnBlock itemUseOnBlock = AdvancedItemSettings.ITEM_USE_ON_BLOCK.get(this.getObject());
		if (itemUseOnBlock != null) {
			itemUseOnBlock.apply(context);
		}
	}

	@Inject(method = "useOnEntity", at = @At("HEAD"))
	private void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemUseOnEntity itemUseOnEntity = AdvancedItemSettings.ITEM_USE_ON_ENTITY.get(this.getObject());
		if (itemUseOnEntity != null) {
			itemUseOnEntity.apply(stack, user, entity, hand);
		}
	}

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		ItemPostHit itemPostHit = AdvancedItemSettings.ITEM_POST_HIT.get(this.getObject());
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		ItemPostMine itemPostMine = AdvancedItemSettings.ITEM_POST_MINE.get(this.getObject());
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}

	@Inject(method = "getUseAction", at = @At("TAIL"), cancellable = true)
	private void getUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
		boolean eatable = AdvancedItemSettings.EATABLE.get(this.getObject());
		if (eatable) cir.setReturnValue(UseAction.EAT);
		boolean drinkable = AdvancedItemSettings.DRINKABLE.get(this.getObject());
		if (drinkable) cir.setReturnValue(UseAction.DRINK);
	}

	@Nullable
	@Override
	public GlintPackView getGlintPackView() {
		return AdvancedItemSettings.GLINT_PACK.get(this.getObject());
	}
}
