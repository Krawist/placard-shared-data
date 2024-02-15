package org.placard.remote

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.placard.config.AccreditationItemTypeOfAccess

@Introspected
@Serdeable
internal data class AccreditationItemCreationRequest(
    val accreditedItem : String,
    val typeOfAccess : AccreditationItemTypeOfAccess = AccreditationItemTypeOfAccess.ALL_DESCENDANT_OF_LEVEL
)