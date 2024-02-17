package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.placard.models.access.AccreditationItemTypeOfAccess
import java.util.UUID

@Introspected
@Serdeable
internal data class AccreditationItemCreationRequest(
    val accreditedItem : UUID,
    val typeOfAccess : AccreditationItemTypeOfAccess = AccreditationItemTypeOfAccess.ALL_DESCENDANT_OF_LEVEL
)