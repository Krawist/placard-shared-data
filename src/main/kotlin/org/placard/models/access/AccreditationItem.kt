package org.placard.models.access

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.placard.config.AccreditationItemTypeOfAccess
import org.placard.models.hierarchy.HierarchyItem

@Introspected
@Serdeable
@Entity(name = "accreditation_items")
internal data class AccreditationItem(

    @Id
    val identifier : String,

    @ManyToOne
    val hierarchyItem : HierarchyItem,

    @ManyToOne
    val accreditation: Accreditation,

    @Enumerated(EnumType.STRING)
    val typeOfAccess : AccreditationItemTypeOfAccess = AccreditationItemTypeOfAccess.ALL_DESCENDANT_OF_LEVEL
)