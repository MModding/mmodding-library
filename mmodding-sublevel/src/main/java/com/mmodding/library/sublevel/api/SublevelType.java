package com.mmodding.library.sublevel.api;

import com.mmodding.library.java.api.function.Mapper;
import com.mmodding.library.sublevel.impl.SublevelTypeImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

public interface SublevelType<A> {

	static <A> SublevelType<A> create(Mapper<A, String> mapper, ResourceKey<LevelStem> stem, int chunkSquareRadius) {
		return new SublevelTypeImpl<>(mapper, stem, chunkSquareRadius);
	}

	/**
	 * The codec for the attachment. It allows mapping a sublevel data from an attachment.
	 * <br>The parsed content is used to resolve a directory! As such, do not use non-conventional characters.
	 * @return the codec
	 */
	Mapper<A, String> attachmentStringMapper();

	ResourceKey<Level> dimension();

	ResourceKey<LevelStem> stem();

	int chunkSquareRadius();

	/**
	 * Creates a sublevel from a parent level (which can be a sublevel too, if you like inception).
	 * <br>The parent level only delegates the properties given by the server. It is the attachment that matters.
	 * <br>If a sublevel of this attachment exists, it is simply retrieved.
	 * @param parent the parent level
	 * @param attachment the attachment
	 * @return the sublevel
	 */
	ServerLevel getOrCreate(ServerLevel parent, A attachment);
}
