package com.mmodding.mmodding_lib.library.items;

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

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomFluidInteractableItem extends Item implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final FluidPickup fluidPickup;

    public CustomFluidInteractableItem(FluidPickup fluidPickup, Settings settings) {
        super(settings);
		this.fluidPickup = fluidPickup;
    }

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockHitResult hitResult = CustomFluidInteractableItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = hitResult.getBlockPos();
			if (!world.canPlayerModifyAt(user, pos)) {
				ItemStack stack = this.fluidPickup.getFilledStack(user.getStackInHand(hand), world.getFluidState(pos), world, pos);
				if (!stack.isEmpty()) {
					world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
					world.emitGameEvent(user, GameEvent.FLUID_PICKUP, pos);
					user.incrementStat(Stats.USED.getOrCreateStat(this));
					ItemUsage.exchangeStack(user.getStackInHand(hand), user, stack);
					return TypedActionResult.success(stack, world.isClient());
				}
			}
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
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
