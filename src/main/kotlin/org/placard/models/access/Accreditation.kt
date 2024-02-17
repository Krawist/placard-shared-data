package org.placard.models.access

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import org.placard.models.Auditable
import org.placard.models.data.SharableData
import org.placard.models.project.Project
import org.placard.models.user.AbstractUser
import java.time.Instant

@Introspected
@Serdeable
@Entity(name = "accreditations")
internal data class Accreditation(

    @Column(name = "level")
    val level: Int,

    @ManyToOne
    val project: Project? = null,

    @ManyToOne
    val abstractUser: AbstractUser? = null,

    @ManyToOne
    val sharableData: SharableData? = null,

    @Enumerated(value = EnumType.STRING)
    val accreditationStatus: AccreditationStatus = AccreditationStatus.ACTIVE,

    val deactivationDate : Instant? = null

) : Auditable()
