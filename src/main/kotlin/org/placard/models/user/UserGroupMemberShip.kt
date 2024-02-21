package org.placard.models.user

import io.micronaut.data.annotation.DateCreated
import jakarta.persistence.Column
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

    @Enumerated(value = EnumType.STRING)
    val status: UserGroupMemberShipStatus = UserGroupMemberShipStatus.ACTIVE,

    @DateCreated
    val addedAt: Instant? = null,

    @DateCreated
    val lastUpdateAt: Instant? = null,

    @Column(name = "last_update_by")
    val lastUpdatedBy: UUID? = null,

    @Column(name = "added_by")
    val addedBy: UUID? = null,
)

@Embeddable
internal data class UserGroupMemberShipKey(
    val userGroupUuid: UUID,
    val userUuid: UUID,
) : Serializable