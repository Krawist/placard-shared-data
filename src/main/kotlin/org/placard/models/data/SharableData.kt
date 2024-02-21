package org.placard.models.data

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable
import org.placard.models.project.Project
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "sharable_data")
internal data class SharableData(

    @Id
    val uuid: UUID,

    @Column(name = "file_system_path")
    val fileSystemPath : String,

    @Column(name = "file_name")
    val displayName : String,

    @Enumerated(EnumType.STRING)
    val accessMode : SharableDataAccessMode,

    @Column(name = "project_uuid")
    val projectUuid: UUID,

    ) : Auditable()