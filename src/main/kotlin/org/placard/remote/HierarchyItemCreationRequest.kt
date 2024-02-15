package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column

@Introspected
@Serdeable
data class HierarchyItemCreationRequest(
    val level: Int,
    val displayName: String,
    val hierarchyIdentifier: String,
    val parentIdentifier: String? = null,
)
