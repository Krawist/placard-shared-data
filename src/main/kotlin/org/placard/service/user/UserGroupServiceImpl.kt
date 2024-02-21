package org.placard.service.user

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.user.UserGroup
import org.placard.models.user.UserGroupMemberShip
import org.placard.models.user.UserGroupMemberShipKey
import org.placard.models.user.UserGroupMemberShipStatus
import org.placard.remote.UserGroupCreationRequest
import org.placard.repositories.UserGroupMemberShipRepository
import org.placard.repositories.UserGroupRepository
import org.placard.repositories.UserRepository
import java.util.UUID

@Singleton
internal class UserGroupServiceImpl(
    private val userGroupRepository: UserGroupRepository,
    private val userGroupMemberShipRepository: UserGroupMemberShipRepository,
    private val userRepository: UserRepository,
) : UserGroupService {

    override fun create(userGroupCreationRequest: UserGroupCreationRequest): HttpResponse<UserGroup> {
        require(userGroupCreationRequest.displayName.isNotBlank()) { "The group name cannot be blank" }

        val userGroup = UserGroup(
            uuid = UUID.randomUUID(),
            displayName = userGroupCreationRequest.displayName
        )

        userGroupRepository.save(userGroup)

        if (userGroupCreationRequest.users.isNotEmpty()) {
            addUsers(usersUuid = userGroupCreationRequest.users, userGroupUuid = userGroup.uuid!!)
        }

        return HttpResponse.created(userGroup)
    }

    override fun search(): HttpResponse<Page<UserGroup>> {
        return HttpResponse.ok(userGroupRepository.findAll(Pageable.unpaged()))
    }

    override fun addUsers(usersUuid: List<UUID>, userGroupUuid: UUID): HttpResponse<UserGroup> {
        val userGroupMemberships = buildUserGroupMemberShip(userGroupUuid = userGroupUuid, usersUuid = usersUuid)

        userGroupMemberships.forEach { userGroupMembership ->
            userGroupMemberShipRepository.findById(userGroupMembership.userGroupMemberShipKey).ifPresent {
                throw IllegalArgumentException("User ${it.userGroupMemberShipKey.userUuid} already present in the group ${it.userGroupMemberShipKey.userGroupUuid}")
            }
        }

        userGroupMemberShipRepository.saveAll(userGroupMemberships)

        return HttpResponse.ok()
    }

    override fun removeUsers(usersUuid: List<UUID>, userGroupUuid: UUID): HttpResponse<UserGroup> {
        val userGroupMemberships = buildUserGroupMemberShip(usersUuid = usersUuid, userGroupUuid = userGroupUuid)

        userGroupMemberships.forEach { userGroupMembership ->
            userGroupMemberShipRepository.findById(userGroupMembership.userGroupMemberShipKey).orElseThrow {
                IllegalArgumentException("User ${userGroupMembership.userGroupMemberShipKey.userUuid} not present in the group ${userGroupMembership.userGroupMemberShipKey.userGroupUuid}")
            }
        }

        userGroupMemberShipRepository.updateAll(userGroupMemberships.map { it.copy(status = UserGroupMemberShipStatus.REMOVED) })

        return HttpResponse.ok()
    }

    private fun buildUserGroupMemberShip(usersUuid: List<UUID>, userGroupUuid: UUID): List<UserGroupMemberShip> {
        val userGroup = userGroupRepository.findById(userGroupUuid).orElseThrow {
            IllegalArgumentException("Invalid group provided. Group with id $userGroupUuid not found.")
        }

        val users = usersUuid.map {
            userRepository.findById(it).orElseThrow {
                IllegalArgumentException("Invalid user provided. User with id $it not found")
            }
        }

        return users.map {
            UserGroupMemberShip(
                userGroupMemberShipKey = UserGroupMemberShipKey(userUuid = it.uuid!!, userGroupUuid = userGroup.uuid!!),
            )
        }
    }
}