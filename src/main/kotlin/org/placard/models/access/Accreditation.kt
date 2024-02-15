package org.placard.models.access

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import org.placard.models.data.SharableData
import org.placard.models.project.Project
import org.placard.models.user.AbstractUser
import org.placard.models.user.User
import java.time.Instant

@Introspected
@Serdeable
@Entity(name = "accreditations")
internal data class Accreditation(

    @Id
    val identifier : String? = null,

    @Column(name = "level")
    val level: Int? = null,

    @ManyToOne
    val project: Project? = null,

    @ManyToOne
    val abstractUser: AbstractUser? = null,

    @ManyToOne
    val sharableData: SharableData? = null,

    @Enumerated(value = EnumType.STRING)
    val accreditationStatus: AccreditationStatus = AccreditationStatus.ACTIVE,

    @DateCreated
    val dateAssigment : Instant? = null,

    @DateUpdated
    val dateLastUpdate : Instant? = null,

    @ManyToOne
    val assignBy : User? = null,

    @ManyToOne
    val lastUpdateBy : User? = null,

    val dateDeactivation : Instant? = null
)
