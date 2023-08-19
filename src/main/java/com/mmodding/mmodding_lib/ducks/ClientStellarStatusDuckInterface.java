package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.stellar.client.ClientStellarStatus;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface ClientStellarStatusDuckInterface extends StellarStatusDuckInterface {

    void mmodding_lib$setStellarStatus(Identifier identifier, ClientStellarStatus stellarStatus);
}
