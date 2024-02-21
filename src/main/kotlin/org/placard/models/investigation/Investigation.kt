package org.placard.models.investigation

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.placard.models.Auditable
import org.placard.models.project.Project
import java.util.UUID

@Serdeable
@Introspected
@Entity(name = "investigations")
internal data class Investigation(

    @Id
    val uuid: UUID,

    @Column(name = "investigation_name")
    val displayName : String,

    @Column(name = "project_uuid")
    val projectUuid: UUID
) : Auditable()
