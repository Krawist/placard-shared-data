package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@Serdeable
@Introspected
internal data class UserGroupCreationRequest(
    val displayName : String,
    val users : List<UUID> = emptyList(),
    val accreditations : List<AccreditationCreationRequest> = emptyList(),
    val roles : List<UUID> = emptyList()
)
