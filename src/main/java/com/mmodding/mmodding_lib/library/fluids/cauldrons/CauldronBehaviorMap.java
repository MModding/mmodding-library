package com.mmodding.mmodding_lib.library.fluids.cauldrons;

import com.mmodding.mmodding_lib.library.base.MModdingBootStrapInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @apiNote Must be used in a BootStrap Entrypoint
 * @see MModdingBootStrapInitializer
 */
public class CauldronBehaviorMap extends HashMap<Item, CauldronBehavior> {

	public static final Map<Item, CauldronBehavior> FILL_BEHAVIORS = new HashMap<>();

	private CauldronBehaviorMap(Map<? extends Item, ? extends CauldronBehavior> m) {
		super(m);
	}

	public static CauldronBehaviorMap of(Map<? extends Item, ? extends CauldronBehavior> behaviorMap) {
		return new CauldronBehaviorMap(behaviorMap);
	}

	public static CauldronBehaviorMap create() {
		return CauldronBehaviorMap.of(CauldronBehavior.createMap());
	}

	public void addFillCauldronBehavior(Item bucketItem, BlockState cauldronState, SoundEvent soundEvent) {
		CauldronBehaviorMap.FILL_BEHAVIORS.put(bucketItem, (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(
			world, pos, player, hand, stack, cauldronState, soundEvent
		));
	}

	public void addEmptyCauldronBehavior(Item bucketItem, Predicate<BlockState> emptyCondition, SoundEvent soundEvent) {
		this.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(
			state, world, pos, player, hand, stack, new ItemStack(bucketItem), emptyCondition, soundEvent
		));
	}
}
