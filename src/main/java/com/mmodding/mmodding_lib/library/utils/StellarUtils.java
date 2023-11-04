package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.ducks.ServerWorldDuckInterface;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class StellarUtils {

	public static StellarStatus getStatusInWorld(ServerWorld world, Identifier status) {
		return ((ServerWorldDuckInterface) world).mmodding_lib$getStellarStatuses().getMap().get(status);
	}
}
