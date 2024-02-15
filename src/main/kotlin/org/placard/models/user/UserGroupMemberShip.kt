package org.placard.models.user

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import java.io.Serializable
import java.time.Instant
import java.util.UUID

@Entity(name = "user_group_membership")
internal data class UserGroupMemberShip(
    @EmbeddedId
    val userGroupMemberShipKey: UserGroupMemberShipKey,

    @DateCreated
    val addedOn : Instant? = null,

    @DateUpdated
    val lastUpdateAt : Instant? = null,

    @ManyToOne
    val addedBy : User? = null,

    @ManyToOne
    val lastUpdateBy : User? = null,

    @Enumerated(value = EnumType.STRING)
    val status : UserGroupMemberShipStatus = UserGroupMemberShipStatus.ACTIVE
)

@Embeddable
internal data class UserGroupMemberShipKey(
    val userGroupUuid: UUID,
    val userUuid: UUID
) : Serializable

internal enum class UserGroupMemberShipStatus {
    ACTIVE, DISABLED, REMOVED
}