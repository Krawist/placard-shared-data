package org.placard.models.user

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "accounts")
internal data class User(

    @Id
    override val uuid: UUID,

    @Column(name = "first_name")
    val firstName: String = "",

    @Column(name = "last_name")
    val lastName: String = "",

    @Column(name = "password")
    val password: String? = null,

    @Column(name = "email_address")
    val emailAddress: String = "",
) : AbstractUser(uuid = uuid)
