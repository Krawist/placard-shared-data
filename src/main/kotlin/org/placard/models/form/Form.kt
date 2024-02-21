package org.placard.models.form

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.placard.models.Auditable
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "forms")
internal data class Form(

    @Id
    val uuid: UUID,

    @Column(name = "form_name")
    val displayName: String,

    @Column(name = "investigation_uuid")
    val investigationUuid: UUID? = null,

    @Column(name = "investigation_step_uuid")
    val investigationStepUuid : UUID? = null,

    @Column(name = "form_status")
    @Enumerated(EnumType.STRING)
    val formStatus: FormStatus,

    @Column(name = "form_event_type")
    @Enumerated(value = EnumType.STRING)
    val eventType: FormEventType

) : Auditable()
