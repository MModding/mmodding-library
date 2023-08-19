package com.mmodding.mmodding_lib.library.stellar.client;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ClientStellarStatus extends StellarStatus {

    public ClientStellarStatus(long fullRotationTime) {
        super(fullRotationTime);
    }

    public static ClientStellarStatus of(long currentTime, long totalTime) {
        ClientStellarStatus clientStellarStatus = new ClientStellarStatus(totalTime);
        clientStellarStatus.setCurrentTime(currentTime);
        return clientStellarStatus;
    }
}
