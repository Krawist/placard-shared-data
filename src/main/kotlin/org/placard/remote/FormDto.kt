package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.placard.models.form.FormEventType
import java.util.UUID

@Introspected
@Serdeable
data class FormDto(
    val uuid: UUID? = null,
    val displayName : String,
    val projectUuid : UUID? = null,
    val investigationStepUuid: UUID? = null,
    val eventType: FormEventType
)
