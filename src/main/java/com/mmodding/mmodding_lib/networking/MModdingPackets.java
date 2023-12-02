package com.mmodding.mmodding_lib.networking;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;

public class MModdingPackets {

	public static final MModdingIdentifier C2S_REQUESTS = new MModdingIdentifier("networking/requests/c2s");

	public static final MModdingIdentifier C2S_RESPONSES = new MModdingIdentifier("networking/responses/c2s");

	public static final MModdingIdentifier S2C_REQUESTS = new MModdingIdentifier("networking/requests/s2c");

	public static final MModdingIdentifier S2C_RESPONSES = new MModdingIdentifier("networking/responses/s2c");

	public static final MModdingIdentifier SYNCABLE_DATA = new MModdingIdentifier("networking/entity/data/syncable");

	public static final MModdingIdentifier CONFIGS = new MModdingIdentifier("networking/configs");

	public static final MModdingIdentifier GLINT_PACKS = new MModdingIdentifier("networking/glint_packs");

	public static final MModdingIdentifier STELLAR_STATUS = new MModdingIdentifier("networking/stellar_status");
}
