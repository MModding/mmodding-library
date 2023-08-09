package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.glint.GlintPackView;
import org.jetbrains.annotations.Nullable;

public interface ItemGlintPack {

    @Nullable
    default GlintPackView getGlintPackView() {
        throw new AssertionError();
    }
}
