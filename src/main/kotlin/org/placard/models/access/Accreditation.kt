package org.placard.models.access

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.placard.models.Auditable
import org.placard.models.data.SharableData
import java.time.Instant
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "accreditations")
internal data class Accreditation(

    @Id
    val uuid: UUID,

    @Column(name = "level")
    val level: Int,

    @Column(name = "project_uuid")
    val projectUuid: UUID? = null,

    @Column(name = "abstract_user_uuid")
    val abstractUserUuid: UUID? = null,

    @Column(name = "sharable_data_uuid")
    val sharableDataUuid: UUID? = null,

    @Enumerated(value = EnumType.STRING)
    val accreditationStatus: AccreditationStatus = AccreditationStatus.ACTIVE,

    @Column(name = "deactivation_date")
    val deactivationDate : Instant? = null

) : Auditable()
