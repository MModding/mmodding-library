package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.library.network.request.c2s.ClientPendingRequestManager;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkIdentifier;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
@ApiStatus.Internal
public class ClientPendingRequestManagers {

	public static final ClientPendingRequestManager<NetworkIdentifier> GLINT_PACK_MANAGER = new ClientPendingRequestManager<>(new MModdingIdentifier("glint_pack"), 300);

	public static void register() {}
}
