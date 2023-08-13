package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ServerElementsInitializer;
import com.mmodding.mmodding_lib.server.MModdingLibServer;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;

import java.util.List;

public interface MModdingServerModInitializer extends DedicatedServerModInitializer {

    @Nullable
    Config getServerConfig();

    List<ServerElementsInitializer> getServerElementsInitializers();

    @Override
    default void onInitializeServer(ModContainer mod) {
        if (this.getServerConfig() != null) {
            this.getServerConfig().initializeConfig();
            MModdingLibServer.SERVER_CONFIGS.put(this.getServerConfig().getQualifier(), this.getServerConfig());
        }
        this.getServerElementsInitializers().forEach(ServerElementsInitializer::registerServer);
        this.onInitializeServer(AdvancedModContainer.of(mod));
    }

    void onInitializeServer(AdvancedModContainer mod);
}
