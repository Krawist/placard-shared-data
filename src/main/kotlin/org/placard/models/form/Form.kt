package org.placard.models.form

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable
import org.placard.models.investigation.InvestigationStep
import org.placard.models.project.Project

@Introspected
@Serdeable
@Entity(name = "forms")
internal data class Form(
    @Column(name = "form_name")
    val displayName: String,

    @ManyToOne
    val project: Project? = null,

    @ManyToOne
    val investigationStep : InvestigationStep? = null

) : Auditable()
