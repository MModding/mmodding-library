package com.mmodding.mmodding_lib.library.blockentities.display;

import com.mmodding.mmodding_lib.library.texts.LocatedText;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Experimental
public interface BlockEntityDisplayExtensions {

	List<LocatedText> getDisplayedTexts();
}
