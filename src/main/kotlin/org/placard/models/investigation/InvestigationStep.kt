package org.placard.models.investigation

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.placard.models.Auditable
import java.util.UUID

@Serdeable
@Introspected
@Entity(name = "investigation_steps")
internal data class InvestigationStep(

    @Id
    val uuid: UUID,

    @Column(name = "investigation_step_name")
    val displayName : String,

    @Column(name = "investigation_uuid")
    val investigationUuid: UUID
) : Auditable()
