package org.placard.models.roles

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.placard.models.Auditable
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "roles")
internal data class Role(

    @Id
    val uuid: UUID,

    @Column(name = "role_name")
    val displayName : String,
) : Auditable()
