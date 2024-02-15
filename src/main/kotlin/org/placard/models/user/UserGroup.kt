package org.placard.models.user

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Introspected
@Serdeable
@Entity(name = "users_group")
internal data class UserGroup(

    @Column(name = "group_name")
    val displayName: String = ""
) : AbstractUser()
