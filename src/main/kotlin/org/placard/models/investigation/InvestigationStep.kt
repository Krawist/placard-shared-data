package org.placard.models.investigation

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable

@Serdeable
@Introspected
@Entity(name = "investigation_steps")
internal data class InvestigationStep(
    @Column(name = "investigation_step_name")
    val displayName : String,

    @ManyToOne
    val investigation: Investigation
) : Auditable()
