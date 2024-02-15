package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
internal data class UserCreationRequest(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
)
