package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Introspected
@Serdeable
internal data class HierarchyCreationRequest(
    @field:NotBlank
    @field:Size(max = 100, min = 3)
    val name : String,
)