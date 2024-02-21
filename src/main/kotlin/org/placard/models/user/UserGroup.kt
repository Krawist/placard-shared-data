package org.placard.models.user

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "users_group")
internal data class UserGroup(

    @Id
    override val uuid: UUID,

    @Column(name = "group_name")
    val displayName: String = ""
) : AbstractUser(uuid = uuid)
