package org.placard.models.roles

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Introspected
@Serdeable
@Entity(name = "roles")
data class Role(
    @Id
    val identifier : String,

    @Column(name = "role_name")
    val displayName : String,

    @OneToMany
    val authorisations: Collection<Authorisation>,
)
