package com.mmodding.library.item.api.catalog;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FluidInteractableItem extends Item {

	private final FluidPickup fluidPickup;

	public FluidInteractableItem(FluidPickup fluidPickup, Settings settings) {
		super(settings);
		this.fluidPickup = fluidPickup;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockHitResult hitResult = FluidInteractableItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = hitResult.getBlockPos();
			if (world.canPlayerModifyAt(user, pos)) {
				ItemStack stack = this.fluidPickup.getFilledStack(user.getStackInHand(hand), world.getFluidState(pos), world, pos);
				if (!stack.isEmpty()) {
					world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
					world.emitGameEvent(user, GameEvent.FLUID_PICKUP, pos);
					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.success(ItemUsage.exchangeStack(user.getStackInHand(hand), user, stack), world.isClient());
				}
			}
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
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
		ItemStack getFilledStack(ItemStack stack, FluidState state, World world, BlockPos pos);
	}
}
