package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
internal data class ProjectCreationRequest(
    val name: String,
    val hierarchyIdentifier: String,
    val parentProjectId: String? = null,
)
