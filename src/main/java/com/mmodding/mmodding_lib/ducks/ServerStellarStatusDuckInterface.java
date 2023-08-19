package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface ServerStellarStatusDuckInterface extends StellarStatusDuckInterface {

    Map<Identifier, StellarStatus> mmodding_lib$getAllStellarStatus();
}
