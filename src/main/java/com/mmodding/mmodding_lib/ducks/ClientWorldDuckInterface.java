package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.stellar.client.StellarObject;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import com.mmodding.mmodding_lib.states.readable.ReadableStellarStatuses;

@InternalOf(StellarObject.class)
public interface ClientWorldDuckInterface {

	ReadableStellarStatuses mmodding_lib$getStellarStatusesAccess();
}
