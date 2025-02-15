package com.mmodding.library.config.api.element.type;

import com.mmodding.library.config.api.content.ConfigContent;
import org.quiltmc.parsers.json.JsonWriter;

import java.io.IOException;

@FunctionalInterface
public interface ConfigElementTypeWriter {

	void write(JsonWriter writer, ConfigContent category, String qualifier) throws IOException;
}
