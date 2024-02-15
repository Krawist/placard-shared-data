package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.placard.models.data.SharableDataAccessMode

@Introspected
@Serdeable
internal data class DataToUpload(
    val displayName : String,
    val accessMode : SharableDataAccessMode,
    val projectIdentifier: String,
    val uploadBy : String
)
