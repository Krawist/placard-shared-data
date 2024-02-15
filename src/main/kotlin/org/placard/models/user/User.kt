package org.placard.models.user

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Introspected
@Serdeable
@Entity(name = "accounts")
internal data class User(
    @Column(name = "first_name")
    val firstName: String = "",

    @Column(name = "last_name")
    val lastName: String = "",

    @Column(name = "password")
    val password: String? = null,

    @Column(name = "email_address")
    val emailAddress: String = "",
) : AbstractUser()
