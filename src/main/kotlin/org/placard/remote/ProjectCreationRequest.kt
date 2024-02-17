package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@Introspected
@Serdeable
internal data class ProjectCreationRequest(
    val name: String,
    val hierarchyUuid: UUID,
    val parentProjectUuid: UUID? = null,
)
