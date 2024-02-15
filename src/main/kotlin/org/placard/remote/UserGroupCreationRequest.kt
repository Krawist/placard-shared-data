package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
internal data class UserGroupCreationRequest(
    val displayName : String,
    val users : List<String> = emptyList(),
    val accreditations : List<AccreditationCreationRequest> = emptyList(),
    val roles : List<String> = emptyList()
)
