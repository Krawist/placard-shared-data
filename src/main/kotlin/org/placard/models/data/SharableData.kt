package org.placard.models.data

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable
import org.placard.models.project.Project

@Introspected
@Serdeable
@Entity(name = "sharable_data")
internal data class SharableData(

    @Column(name = "file_system_path")
    val fileSystemPath : String,

    @Column(name = "file_name")
    val displayName : String,

    @Enumerated(EnumType.STRING)
    val accessMode : SharableDataAccessMode,

    @ManyToOne
    val project: Project,

) : Auditable()