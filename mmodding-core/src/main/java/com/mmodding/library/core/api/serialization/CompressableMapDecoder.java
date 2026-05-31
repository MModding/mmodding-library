package com.mmodding.library.core.api.serialization;

import com.mojang.serialization.CompressorHolder;
import com.mojang.serialization.MapDecoder;

public abstract class CompressableMapDecoder<A> extends CompressorHolder implements MapDecoder<A> {
}
