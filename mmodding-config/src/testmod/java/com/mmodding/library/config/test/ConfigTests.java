package com.mmodding.library.config.test;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.java.api.color.Color;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.StringRepresentable;

public class ConfigTests implements ExtendedModInitializer {

	public static final ConfigSpec SPECIFICATION = ConfigSpec.create()
		.bool("boolean", true)
		.color("color", Color.rgb(0, 0, 0))
		.category("category", category -> category
			.string("string", "value")
			.doubleRange("range", 1.0f, 3.0f, 1.0f)
			.list("list", Codec.INT, ByteBufCodecs.INT, 1, 2, 3, 4, 5, 6)
			.enumValue("enum", TestConfigEnum::values, TestConfigEnum.FIRST_FIELD)
		);

	public static final Config CONFIG = Config.builder("mmodding", "common/mmodding", SPECIFICATION)
		.withLevel(ConfigLevel.LEVEL_LOAD)
		.withNetworkManagement(ConfigNetworkManagement.LOCALLY_MANAGED)
		.build(MModdingLibrary.createId("config"));

	@Override
	public void setupManager(ElementsManager manager) {}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public enum TestConfigEnum implements StringRepresentable {
		FIRST_FIELD,
		SECOND_FIELD;

		@Override
		public String getSerializedName() {
			return this.name().toLowerCase();
		}
	}
}
