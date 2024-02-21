package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.placard.models.data.SharableDataAccessMode
import java.util.UUID

@Introspected
@Serdeable
internal data class DataToUpload(
    val displayName : String,
    val accessMode : SharableDataAccessMode,
    val projectUuid: UUID,
    val uploadBy : UUID
)
