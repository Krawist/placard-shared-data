package org.placard.models.roles

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "authorizations")
data class Authorisation(
    @Id
    val identifier : String,

    @Column(name = "authorization_name")
    val name : String,

    @Column(name = "authorization_group_uuid")
    val groupUuid : UUID
)
