package org.placard.models.hierarchy

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable

@Entity(name = "hierarchy_items")
@Introspected
@Serdeable
internal data class HierarchyItem(

    @Column(name = "level")
    val level: Int,

    @Column(name = "name")
    val displayName: String,

    @ManyToOne
    val hierarchy: Hierarchy,

    @ManyToOne
    val parent: HierarchyItem? = null,

) : Auditable()