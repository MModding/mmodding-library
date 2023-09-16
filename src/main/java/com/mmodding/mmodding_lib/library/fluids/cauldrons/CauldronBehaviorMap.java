package com.mmodding.mmodding_lib.library.fluids.cauldrons;

import com.mmodding.mmodding_lib.library.base.MModdingBootstrapInitializer;
import com.mmodding.mmodding_lib.library.fluids.buckets.CustomBucketItem;
import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @apiNote Must be used in a BootStrap Entrypoint
 * @see MModdingBootstrapInitializer
 */
public class CauldronBehaviorMap extends Object2ObjectOpenHashMap<Item, CauldronBehavior> {

	public static final BiList<Item, CauldronBehavior> FILL_BEHAVIORS = new BiArrayList<>();

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

	public void addFillCauldronBehavior(BlockState cauldronState, SoundEvent soundEvent, Item... bucketItems) {
		for (Item bucketItem : bucketItems) {
			CauldronBehaviorMap.FILL_BEHAVIORS.add(
				bucketItem instanceof CustomBucketItem bucket ? bucket.getManager().getFilledItemOrDefault(new ItemStack(bucketItem)).getItem() : bucketItem,
				(state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(
					world, pos, player, hand, stack, cauldronState, soundEvent
				)
			);
		}
	}

	public void addEmptyCauldronBehavior(Predicate<BlockState> emptyCondition, SoundEvent soundEvent, Item... bucketItems) {
		for (Item bucketItem : bucketItems) {
			this.put(
				bucketItem instanceof CustomBucketItem bucket ? bucket.getManager().getEmptiedItemOrDefault(new ItemStack(Items.BUCKET)).getItem() : Items.BUCKET,
				(state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(
					state, world, pos, player, hand, stack, bucketItem instanceof CustomBucketItem bucket ? bucket.getManager().getFilledItemOrDefault(new ItemStack(bucketItem)) : new ItemStack(bucketItem), emptyCondition, soundEvent
				)
			);
		}
	}

	public void addBucketBehaviors() {
		CauldronBehavior.registerBucketBehavior(this);
	}
}
