package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.UserGroupMemberShip
import org.placard.models.user.UserGroupMemberShipKey
import org.placard.models.user.UserGroupMemberShipStatus
import java.util.UUID

@MongoRepository
internal interface UserGroupMemberShipRepository : PageableRepository<UserGroupMemberShip, UserGroupMemberShipKey> {

    fun findByUserGroupMemberShipKeyUserUuidAndStatus(userUuid: UUID, status: UserGroupMemberShipStatus): List<UserGroupMemberShip>

}