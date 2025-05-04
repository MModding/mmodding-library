package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ServerElementsInitializer;
import com.mmodding.mmodding_lib.server.MModdingLibServer;
import net.fabricmc.api.DedicatedServerModInitializer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MModdingServerModInitializer extends DedicatedServerModInitializer {

    @Nullable
    Config getServerConfig();

    List<ServerElementsInitializer> getServerElementsInitializers();

    @Override
    default void onInitializeServer() {
        if (this.getServerConfig() != null) {
            this.getServerConfig().initializeConfig();
            MModdingLibServer.SERVER_CONFIGS.put(this.getServerConfig().getQualifier(), this.getServerConfig());
        }
        this.getServerElementsInitializers().forEach(ServerElementsInitializer::registerServer);
        this.onInitializeServer(AdvancedModContainer.of(MModdingLib.getModContainer(this.getClass(), "server", DedicatedServerModInitializer.class)));
    }

    void onInitializeServer(AdvancedModContainer mod);
}
