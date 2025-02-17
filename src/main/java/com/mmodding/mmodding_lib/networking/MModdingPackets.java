package com.mmodding.mmodding_lib.networking;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;

public class MModdingPackets {

	public static final MModdingIdentifier C2S_REQUESTS = new MModdingIdentifier("networking/requests/c2s");

	public static final MModdingIdentifier C2S_RESPONSES = new MModdingIdentifier("networking/responses/c2s");

	public static final MModdingIdentifier S2C_REQUESTS = new MModdingIdentifier("networking/requests/s2c");

	public static final MModdingIdentifier S2C_RESPONSES = new MModdingIdentifier("networking/responses/s2c");

	public static final MModdingIdentifier SYNCABLE_DATA = new MModdingIdentifier("networking/entity/data/syncable");

	public static final MModdingIdentifier CONFIGS = new MModdingIdentifier("networking/configs");

	public static final MModdingIdentifier STELLAR_STATUS = new MModdingIdentifier("networking/stellar_status");

	public static final MModdingIdentifier APPEND_SOUNDTRACKS = new MModdingIdentifier("networking/soundtracks/append");

	public static final MModdingIdentifier SEND_SOUNDTRACKS = new MModdingIdentifier("networking/soundtracks/send");

	public static final MModdingIdentifier SKIP_SOUNDTRACKS = new MModdingIdentifier("networking/soundtracks/skip");

	public static final MModdingIdentifier SKIP_TO_PART_SOUNDTRACKS = new MModdingIdentifier("networking/soundtracks/skip_to_part");

	public static final MModdingIdentifier CLEAR_SOUNDTRACKS = new MModdingIdentifier("networking/soundtracks/clear");
}
