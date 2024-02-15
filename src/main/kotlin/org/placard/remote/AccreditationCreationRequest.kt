package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
internal data class AccreditationCreationRequest(
    val level: Int,
    val items: List<AccreditationItemCreationRequest>,
    val projectIdentifier: String? = null,
)
