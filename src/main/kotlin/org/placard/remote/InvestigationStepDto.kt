package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@Introspected
@Serdeable
data class InvestigationStepDto(
    val uuid: UUID? = null,
    val displayName : String,
    val investigationUuid : UUID
)
