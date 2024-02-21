package org.placard.models.roles

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "authorization_groups")
data class AuthorisationGroup(
    @Id
    val uuid : UUID,

    @Column(name = "name")
    val displayName : String
)
