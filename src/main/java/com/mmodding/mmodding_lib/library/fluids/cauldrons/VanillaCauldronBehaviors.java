package com.mmodding.mmodding_lib.library.fluids.cauldrons;

import com.mmodding.mmodding_lib.library.base.MModdingBootstrapInitializer;

/**
 * @apiNote Must be used in a BootStrap Entrypoint
 * @see MModdingBootstrapInitializer
 */
public class VanillaCauldronBehaviors {

	public static final VanillaCauldronBehaviorMap EMPTY_BEHAVIOR = new VanillaCauldronBehaviorMap();
	public static final VanillaCauldronBehaviorMap WATER_BEHAVIOR = new VanillaCauldronBehaviorMap();
	public static final VanillaCauldronBehaviorMap LAVA_BEHAVIOR = new VanillaCauldronBehaviorMap();
	public static final VanillaCauldronBehaviorMap SNOW_POWDER_BEHAVIOR = new VanillaCauldronBehaviorMap();

	public static class VanillaCauldronBehaviorMap extends CauldronBehaviorMap {

		@Override
		@Deprecated
		public void addBucketBehaviors() {
			throw new UnsupportedOperationException();
		}
	}
}
