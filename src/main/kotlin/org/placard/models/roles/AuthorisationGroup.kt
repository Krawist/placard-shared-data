package org.placard.models.roles

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Introspected
@Serdeable
@Entity(name = "authorization_groups")
data class AuthorisationGroup(

    @Id
    val identifier : String,

    @Column(name = "name")
    val displayName : String
)
