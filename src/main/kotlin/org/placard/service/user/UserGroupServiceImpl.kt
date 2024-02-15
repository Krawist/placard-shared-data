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
    private val userRepository: UserRepository
) : UserGroupService {

    override fun create(userGroupCreationRequest: UserGroupCreationRequest): HttpResponse<UserGroup> {
        require(userGroupCreationRequest.displayName.isNotBlank()) {"The group name cannot be blank"}

        val userGroup = UserGroup(displayName = userGroupCreationRequest.displayName).also {
            it.identifier = UUID.randomUUID().toString()
        }

        userGroupRepository.save(userGroup)

        if(userGroupCreationRequest.users.isNotEmpty()){
            addUsers(usersIdentifier = userGroupCreationRequest.users, userGroupIdentifier = userGroup.identifier)
        }

        return HttpResponse.created(userGroup)
    }

    override fun search(): HttpResponse<Page<UserGroup>> {
        return HttpResponse.ok(userGroupRepository.findAll(Pageable.unpaged()))
    }

    override fun addUsers(usersIdentifier: List<String>, userGroupIdentifier: String): HttpResponse<UserGroup> {
        val userGroupMemberships = buildUserGroupMemberShip(userGroupIdentifier = userGroupIdentifier, usersIdentifier = usersIdentifier)

        userGroupMemberships.forEach { userGroupMembership ->
            userGroupMemberShipRepository.findById(userGroupMembership.userGroupMemberShipKey).ifPresent {
                throw IllegalArgumentException("User ${it.userGroupMemberShipKey.userUuid} already present in the group ${it.userGroupMemberShipKey.userGroupUuid}")
            }
        }

        userGroupMemberShipRepository.saveAll(userGroupMemberships)

        return HttpResponse.ok()
    }

    override fun removeUsers(usersIdentifier: List<String>, userGroupIdentifier: String): HttpResponse<UserGroup> {
        val userGroupMemberships = buildUserGroupMemberShip(usersIdentifier = usersIdentifier, userGroupIdentifier = userGroupIdentifier)

        userGroupMemberships.forEach { userGroupMembership ->
            userGroupMemberShipRepository.findById(userGroupMembership.userGroupMemberShipKey).orElseThrow {
                IllegalArgumentException("User ${userGroupMembership.userGroupMemberShipKey.userUuid} not present in the group ${userGroupMembership.userGroupMemberShipKey.userGroupUuid}")
            }
        }

        userGroupMemberShipRepository.updateAll(userGroupMemberships.map { it.copy(status = UserGroupMemberShipStatus.REMOVED) })

        return HttpResponse.ok()
    }

    private fun buildUserGroupMemberShip(usersIdentifier: List<String>, userGroupIdentifier: String) : List<UserGroupMemberShip>{
        val userGroup = userGroupRepository.findById(userGroupIdentifier).orElseThrow {
            IllegalArgumentException("Invalid group provided. Group with id $userGroupIdentifier not found.")
        }

        val users = usersIdentifier.map {
            userRepository.findById(it).orElseThrow {
                IllegalArgumentException("Invalid user provided. User with id $it not found")
            }
        }

        return users.map {
            UserGroupMemberShip(
                userGroupMemberShipKey = UserGroupMemberShipKey(userUuid = UUID.fromString(it.identifier), userGroupUuid = UUID.fromString(userGroup.identifier)),
            )
        }
    }
}