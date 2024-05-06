package com.mmodding.library.item.api.group;

public interface Qualifiable {

	<T> T applyQualifier(ItemGroupQualifier qualifier);
}
