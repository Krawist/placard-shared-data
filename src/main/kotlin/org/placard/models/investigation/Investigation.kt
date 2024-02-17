package org.placard.models.investigation

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable
import org.placard.models.project.Project

@Serdeable
@Introspected
@Entity(name = "investigations")
internal data class Investigation(
    @Column(name = "investigation_name")
    val displayName : String,

    @ManyToOne
    val project: Project
) : Auditable()
