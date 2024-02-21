package org.placard.models.project

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.placard.models.Auditable
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "projects")
internal data class Project(

    @Id
    val uuid: UUID,

    @Column(name = "name")
    val displayName : String,

    @Column(name = "hierarchy_uuid")
    val hierarchyUuid: UUID,

    @Column(name = "parent_project_uuid")
    val parentProjectUuid : UUID? = null
) : Auditable()
