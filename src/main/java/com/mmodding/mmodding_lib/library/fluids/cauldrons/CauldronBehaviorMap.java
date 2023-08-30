package com.mmodding.mmodding_lib.library.fluids.cauldrons;

import com.mmodding.mmodding_lib.library.base.MModdingBootStrapInitializer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @apiNote Must be used in a BootStrap Entrypoint
 * @see MModdingBootStrapInitializer
 */
public class CauldronBehaviorMap extends Object2ObjectOpenHashMap<Item, CauldronBehavior> {

	public static final Map<Item, CauldronBehavior> FILL_BEHAVIORS = new HashMap<>();

	private CauldronBehaviorMap() {
		super();
	}

	public static CauldronBehaviorMap create() {
		return Util.make(new CauldronBehaviorMap(), map -> map.defaultReturnValue((state, world, pos, player, hand, stack) -> ActionResult.PASS));
	}

	public static CauldronBehaviorMap of(Map<? extends Item, ? extends CauldronBehavior> behaviorMap) {
		CauldronBehaviorMap cauldronBehaviorMap = CauldronBehaviorMap.create();
		cauldronBehaviorMap.putAll(behaviorMap);
		return cauldronBehaviorMap;
	}

	public static void addFillCauldronBehavior(Item bucketItem, BlockState cauldronState, SoundEvent soundEvent) {
		CauldronBehaviorMap.FILL_BEHAVIORS.put(bucketItem, (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(
			world, pos, player, hand, stack, cauldronState, soundEvent
		));
	}

	public void addEmptyCauldronBehavior(Item bucketItem, Predicate<BlockState> emptyCondition, SoundEvent soundEvent) {
		this.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(
			state, world, pos, player, hand, stack, new ItemStack(bucketItem), emptyCondition, soundEvent
		));
	}

	public void addBucketBehaviors() {
		CauldronBehavior.registerBucketBehavior(this);
	}
}
