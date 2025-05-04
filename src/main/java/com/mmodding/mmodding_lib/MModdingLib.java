package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import com.mmodding.mmodding_lib.library.base.MModdingModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.MModdingInitializationEvents;
import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.network.support.type.*;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.TextureLocation;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MModdingLib implements ModInitializer {

	public static final List<AdvancedModContainer> MMODDING_MODS = MModdingLib.getMModdingMods();

	public static final Map<String, Config> CONFIGS = new HashMap<>();

	public static final AdvancedModContainer LIBRARY_CONTAINER = AdvancedModContainer.of(FabricLoader.getInstance().getModContainer("mmodding_lib").orElseThrow());

	public static final MModdingLibConfig LIBRARY_CONFIG = new MModdingLibConfig();

	@Override
	public void onInitialize() {

		MModdingInitializationEvents.START.invoker().onMModdingInitializationStart(LIBRARY_CONTAINER);

		LIBRARY_CONFIG.initializeConfig();

		LIBRARY_CONTAINER.getLogger().info("Initialize {}", LIBRARY_CONTAINER.getMetadata().getName());

		Events.register();
		PacketReceivers.register();
		RequestHandlers.register();

		if (LIBRARY_CONFIG.getContent().getBoolean("showMModdingLibraryMods")) {
			String mods = "MModding Library Mods :";
			for (AdvancedModContainer mmoddingMod : MMODDING_MODS) {
				mods = mods.concat(" " + mmoddingMod.getMetadata().getName() + " [" + mmoddingMod.getMetadata().getId() + "],");
			}

			mods = StringUtils.chop(mods);
			LIBRARY_CONTAINER.getLogger().info(mods);
		}

		MModdingInitializationEvents.END.invoker().onMModdingInitializationEnd(LIBRARY_CONTAINER);
	}

	public static <T> ModContainer getModContainer(Class<?> entrypoint, String key, Class<T> type) {
		for (EntrypointContainer<?> container : FabricLoader.getInstance().getEntrypointContainers(key, type)) {
			if (container.getEntrypoint().getClass().equals(entrypoint)) {
				return container.getProvider();
			}
		}
		return null;
	}

	private static List<AdvancedModContainer> getMModdingMods() {
		List<AdvancedModContainer> advancedContainers = new ArrayList<>();

		FabricLoader.getInstance().getEntrypointContainers("main", ModInitializer.class)
			.stream().filter(mod -> mod.getEntrypoint() instanceof MModdingModInitializer)
			.forEachOrdered(mod -> advancedContainers.add(AdvancedModContainer.of(mod.getProvider())));

		return List.copyOf(advancedContainers);
	}

	public static String id() {
		return "mmodding_lib";
	}

	public static Identifier createId(String path) {
		return new Identifier(MModdingLib.id(), path);
	}

	public static TextureLocation getTextureLocation(String path) {
		return new TextureLocation(MModdingLib.id(), path);
	}

	static {
		NetworkSupport.register(new Identifier("java", "boolean"), NetworkBoolean.class, buf -> NetworkBoolean.of(buf.readBoolean()));
		NetworkSupport.register(new Identifier("java", "byte"), NetworkByte.class, buf -> NetworkByte.of(buf.readByte()));
		NetworkSupport.register(new Identifier("java", "byte_array"), NetworkByteArray.class, buf -> NetworkByteArray.of(buf.readByteArray()));
		NetworkSupport.register(new Identifier("java", "double"), NetworkDouble.class, buf -> NetworkDouble.of(buf.readDouble()));
		NetworkSupport.register(new Identifier("java", "float"), NetworkFloat.class, buf -> NetworkFloat.of(buf.readFloat()));
		NetworkSupport.register(new Identifier("minecraft", "identifier"), NetworkIdentifier.class, buf -> NetworkIdentifier.of(buf.readIdentifier()));
		NetworkSupport.register(new Identifier("java", "integer"), NetworkInteger.class, buf -> NetworkInteger.of(buf.readVarInt()));
		NetworkSupport.register(new Identifier("java", "int_array"), NetworkIntegerArray.class, buf -> NetworkIntegerArray.of(buf.readIntArray()));
		NetworkSupport.register(new Identifier("minecraft", "itemstack"), NetworkItemStack.class, buf -> NetworkItemStack.of(buf.readItemStack()));
		NetworkSupport.register(new MModdingIdentifier("list"), NetworkList.class, NetworkList::read);
		NetworkSupport.register(new Identifier("java", "long"), NetworkLong.class, buf -> NetworkLong.of(buf.readVarLong()));
		NetworkSupport.register(new Identifier("java", "long_array"), NetworkLongArray.class, buf -> NetworkLongArray.of(buf.readLongArray()));
		NetworkSupport.register(new MModdingIdentifier("map"), NetworkMap.class, NetworkMap::read);
		NetworkSupport.register(new Identifier("java", "short"), NetworkShort.class, buf -> NetworkShort.of(buf.readShort()));
		NetworkSupport.register(new Identifier("java", "string"), NetworkString.class, buf -> NetworkString.of(buf.readString()));
		NetworkSupport.register(new Identifier("java", "uuid"), NetworkUUID.class, buf -> NetworkUUID.of(buf.readUuid()));
	}
}
