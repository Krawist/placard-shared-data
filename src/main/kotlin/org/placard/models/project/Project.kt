package org.placard.models.project

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable
import org.placard.models.hierarchy.Hierarchy
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "projects")
internal data class Project(
    @Column(name = "name")
    val displayName : String,

    @ManyToOne
    val hierarchy: Hierarchy,

    @ManyToOne
    val parentProject : Project? = null
) : Auditable()
