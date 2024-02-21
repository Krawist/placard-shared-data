package org.placard.models.access

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "accreditation_items")
internal data class AccreditationItem(
    @Id
    val uuid : UUID,

    @Column(name = "hierarchy_item_uuid")
    val hierarchyItemUuid : UUID,

    @Column(name = "accreditation_uuid")
    val accreditationUuid: UUID,

    @Enumerated(EnumType.STRING)
    val typeOfAccess : AccreditationItemTypeOfAccess = AccreditationItemTypeOfAccess.ALL_DESCENDANT_OF_LEVEL
)