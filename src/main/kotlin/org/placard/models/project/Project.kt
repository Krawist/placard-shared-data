package org.placard.models.project

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.placard.models.hierarchy.Hierarchy

@Introspected
@Serdeable
@Entity(name = "projects")
internal data class Project(

    @Id
    val identifier : String? = null,

    @Column(name = "name")
    val name : String? = null,

    @ManyToOne
    val hierarchy: Hierarchy? = null,

    @ManyToOne
    val parent : Project? = null
)
