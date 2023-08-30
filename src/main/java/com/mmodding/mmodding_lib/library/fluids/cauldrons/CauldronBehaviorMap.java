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
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

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

	public void addFillCauldronBehavior(Item bucketItem, BlockState cauldronState, SoundEvent soundEvent) {
		this.addFillCauldronBehavior(Registry.ITEM.getId(bucketItem), cauldronState, soundEvent);
	}

	public void addEmptyCauldronBehavior(Item bucketItem, Predicate<BlockState> emptyCondition, SoundEvent soundEvent) {
		this.addEmptyCauldronBehavior(Registry.ITEM.getId(bucketItem), emptyCondition, soundEvent);
	}

	public void addFillCauldronBehavior(Identifier bucketIdentifier, BlockState cauldronState, SoundEvent soundEvent) {
		CauldronBehaviorMap.FILL_BEHAVIORS.put(Registry.ITEM.get(bucketIdentifier), (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(
			world, pos, player, hand, stack, cauldronState, soundEvent
		));
	}

	public void addEmptyCauldronBehavior(Identifier bucketIdentifier, Predicate<BlockState> emptyCondition, SoundEvent soundEvent) {
		this.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(
			state, world, pos, player, hand, stack, new ItemStack(Registry.ITEM.get(bucketIdentifier)), emptyCondition, soundEvent
		));
	}

	public void addBucketBehaviors() {
		CauldronBehavior.registerBucketBehavior(this);
	}
}
