package com.mmodding.library.item.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FluidInteractableItem extends Item {

	private final FluidPickup fluidPickup;

	public FluidInteractableItem(FluidPickup fluidPickup, Properties settings) {
		super(settings);
		this.fluidPickup = fluidPickup;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		BlockHitResult hitResult = FluidInteractableItem.getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = hitResult.getBlockPos();
			if (world.mayInteract(user, pos)) {
				ItemStack stack = this.fluidPickup.getFilledStack(user.getItemInHand(hand), world.getFluidState(pos), world, pos);
				if (!stack.isEmpty()) {
					world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f);
					world.gameEvent(user, GameEvent.FLUID_PICKUP, pos);
					user.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(user.getItemInHand(hand), user, stack), world.isClientSide());
				}
			}
		}
		return InteractionResultHolder.pass(user.getItemInHand(hand));
	}

	public interface FluidPickup {

		/**
		 * Gets the filled stack after the action of the player.
		 * @param stack the current stack
		 * @param state the fluid state
		 * @param world the world
		 * @param pos the fluid pos
		 * @return the filled stack
		 * @apiNote To prevent the action from happening, simply return {@link ItemStack#EMPTY}.
		 */
		ItemStack getFilledStack(ItemStack stack, FluidState state, Level world, BlockPos pos);
	}
}
