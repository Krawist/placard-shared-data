package org.placard.models.hierarchy

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity(name = "hierarchy_items")
@Introspected
@Serdeable
internal data class HierarchyItem(

    @Id
    val identifier: String,

    @Column(name = "level")
    val level: Int,

    @Column(name = "name")
    val name: String,

    @ManyToOne
    val hierarchy: Hierarchy,

    @ManyToOne
    val parent: HierarchyItem? = null,
)