package org.placard.models.roles

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Introspected
@Serdeable
@Entity(name = "authorizations")
data class Authorisation(
    @Id
    val identifier : String,

    @Column(name = "authorization_name")
    val name : String,

    @ManyToOne
    val groupId : AuthorisationGroup
)
