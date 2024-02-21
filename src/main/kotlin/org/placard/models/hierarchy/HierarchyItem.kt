package org.placard.models.hierarchy

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.placard.models.Auditable
import java.util.UUID

@Entity(name = "hierarchy_items")
@Introspected
@Serdeable
internal data class HierarchyItem(

    @Id
    val uuid: UUID,

    @Column(name = "level")
    val level: Int,

    @Column(name = "name")
    val displayName: String,

    @Column(name = "hierarchy_uuid")
    val hierarchyUuid: UUID,

    @Column(name = "hierarchy_item_uuid")
    val parentUuid: UUID? = null,

    ) : Auditable()